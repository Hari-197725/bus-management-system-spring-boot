package com.project.bus_reservation.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FirebaseAuthWebConfig implements WebMvcConfigurer {

    private final FirebaseAuthFilter firebaseAuthFilter;
    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    @Bean
    public FilterRegistrationBean<FirebaseAuthFilter> firebaseAuthFilterRegistration() {
        FilterRegistrationBean<FirebaseAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(firebaseAuthFilter);
        registration.addUrlPatterns("/api/*");
        registration.setName("firebaseAuthenticationFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        return registration;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }
}
