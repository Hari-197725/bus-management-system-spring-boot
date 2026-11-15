package com.project.bus_reservation.config.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to inject the current authenticated Firebase user into controller handler methods.
 * Can be used with {@link FirebaseUserDetails} or {@link String} (which resolves to the Firebase UID).
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
