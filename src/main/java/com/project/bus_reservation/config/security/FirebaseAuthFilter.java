package com.project.bus_reservation.config.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {

    private final FirebaseAuth firebaseAuth;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CURRENT_USER_ATTRIBUTE = "firebaseCurrentUser";
    public static final String CURRENT_UID_ATTRIBUTE = "firebaseUid";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length()).trim();
            try {
                FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
                FirebaseUserDetails userDetails = FirebaseUserDetails.builder()
                        .uid(decodedToken.getUid())
                        .email(decodedToken.getEmail())
                        .name(decodedToken.getName())
                        .picture(decodedToken.getPicture())
                        .emailVerified(decodedToken.isEmailVerified())
                        .issuer(decodedToken.getIssuer())
                        .claims(decodedToken.getClaims())
                        .build();

                FirebaseUserContext.setCurrentUser(userDetails);
                request.setAttribute(CURRENT_USER_ATTRIBUTE, userDetails);
                request.setAttribute(CURRENT_UID_ATTRIBUTE, userDetails.getUid());

                log.debug("Authenticated Firebase user UID: {}", userDetails.getUid());
            } catch (Exception e) {
                log.warn("Firebase ID Token verification failed: {}", e.getMessage());
                request.setAttribute("firebaseAuthError", e.getMessage());
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            FirebaseUserContext.clear();
        }
    }
}
