package com.project.bus_reservation.config.security;

import com.project.bus_reservation.exception.custom.ForbiddenException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Reusable admin guard.
 * Checks if the currently authenticated Firebase user is the admin.
 * Used by OperatorController, BusController, RouteController.
 */
@Component
public class AdminGuard {

    @Value("${app.admin.uid}")
    private String adminUid;

    /**
     * Throws ForbiddenException if the current user is not the admin.
     */
    public void requireAdmin(FirebaseUserDetails currentUser) {
        if (currentUser == null) {
            throw new ForbiddenException(ErrorCode.UNAUTHORIZED, "You must be logged in to perform this action");
        }
        if (!adminUid.equals(currentUser.getUid())) {
            throw new ForbiddenException(ErrorCode.ACCESS_DENIED, "Only admin can perform this action");
        }
    }

    /**
     * Returns true if the current user is the admin.
     */
    public boolean isAdmin(FirebaseUserDetails currentUser) {
        return currentUser != null && adminUid.equals(currentUser.getUid());
    }
}

