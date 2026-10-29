package com.project.bus_reservation.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseUserDetails {
    private String uid;
    private String email;
    private String name;
    private String picture;
    private boolean emailVerified;
    private String issuer;
    private Map<String, Object> claims;
}
