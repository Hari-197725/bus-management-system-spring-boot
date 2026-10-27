package com.project.bus_reservation.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseTokenService {

    private final FirebaseAuth firebaseAuth;

    /**
     * Verifies the Firebase ID Token string received from client requests.
     *
     * @param idToken The Bearer token from the client's Authorization header
     * @return FirebaseToken containing uid, email, name, claims
     * @throws Exception if the token is invalid or expired
     */
    public FirebaseToken verifyToken(String idToken) throws Exception {
        return firebaseAuth.verifyIdToken(idToken);
    }

    /**
     * Verifies the token and checks if the token has been revoked.
     */
    public FirebaseToken verifyToken(String idToken, boolean checkRevoked) throws Exception {
        return firebaseAuth.verifyIdToken(idToken, checkRevoked);
    }

    /**
     * Extracts Firebase UID from an ID token.
     */
    public String getUid(String idToken) throws Exception {
        return verifyToken(idToken).getUid();
    }

    /**
     * Extracts Email from an ID token.
     */
    public String getEmail(String idToken) throws Exception {
        return verifyToken(idToken).getEmail();
    }

    /**
     * Retrieves Firebase UserRecord by UID.
     */
    public UserRecord getUserRecord(String uid) throws FirebaseAuthException {
        return firebaseAuth.getUser(uid);
    }

    /**
     * Retrieves Firebase UserRecord by email.
     */
    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }

    /**
     * Creates a new user in Firebase Auth.
     */
    public UserRecord createUser(String email, String password, String displayName) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(displayName)
                .setEmailVerified(false);
        return firebaseAuth.createUser(request);
    }

    /**
     * Sets custom claims (e.g., roles like "ROLE_ADMIN", "ROLE_USER") for a Firebase user.
     */
    public void setCustomUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException {
        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    /**
     * Deletes a user in Firebase Auth by UID.
     */
    public void deleteUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }

    /**
     * Creates a custom token for a given UID.
     */
    public String createCustomToken(String uid) throws FirebaseAuthException {
        return firebaseAuth.createCustomToken(uid);
    }
}
