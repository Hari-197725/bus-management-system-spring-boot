package com.project.bus_reservation.config.security;

public final class FirebaseUserContext {

    private static final ThreadLocal<FirebaseUserDetails> CURRENT_USER = new ThreadLocal<>();

    private FirebaseUserContext() {}

    public static void setCurrentUser(FirebaseUserDetails user) {
        CURRENT_USER.set(user);
    }

    public static FirebaseUserDetails getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static String getCurrentUid() {
        FirebaseUserDetails user = CURRENT_USER.get();
        return user != null ? user.getUid() : null;
    }

    public static String getCurrentEmail() {
        FirebaseUserDetails user = CURRENT_USER.get();
        return user != null ? user.getEmail() : null;
    }

    public static void clear() {
        CURRENT_USER.remove();
    }
}
