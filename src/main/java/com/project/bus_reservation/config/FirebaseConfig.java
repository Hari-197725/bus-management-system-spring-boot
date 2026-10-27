package com.project.bus_reservation.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path:classpath:firebase-service-account.json}")
    private Resource serviceAccountResource;

    @Value("${firebase.project.id:bus-reservation-system-4u}")
    private String projectId;

    @Bean
    @Primary
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.info("FirebaseApp already initialized: {}", FirebaseApp.getInstance().getName());
            return FirebaseApp.getInstance();
        }

        GoogleCredentials credentials;
        if (serviceAccountResource != null && serviceAccountResource.exists()) {
            log.info("Initializing FirebaseApp using credentials file: {}", serviceAccountResource.getFilename());
            credentials = GoogleCredentials.fromStream(serviceAccountResource.getInputStream());
        } else {
            log.warn("Firebase credentials resource not found or null, falling back to ApplicationDefault credentials");
            credentials = GoogleCredentials.getApplicationDefault();
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        log.info("FirebaseApp successfully initialized for project: {}", projectId);
        return app;
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
