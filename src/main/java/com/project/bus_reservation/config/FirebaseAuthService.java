package com.project.bus_reservation.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseAuthService {

    private final FirebaseAuth firebaseAuth;

    /**
     * Verifies the Firebase ID Token sent from the client (mobile or web frontend).
     *
     * @param idToken The Firebase JWT token from the client's Authorization header
     * @return FirebaseToken containing UID, email, name, and custom claims
     * @throws FirebaseAuthException if token is invalid or expired
     */
    public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
        return firebaseAuth.verifyIdToken(idToken);
    }

    /**
     * Retrieves user details from Firebase Auth by Firebase UID.
     */
    public UserRecord getUserByUid(String uid) throws FirebaseAuthException {
        return firebaseAuth.getUser(uid);
    }

    /**
     * Retrieves user details from Firebase Auth by email.
     */
    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }

    /**
     * Creates a new user in Firebase Authentication.
     */
    public UserRecord createFirebaseUser(String email, String password, String displayName) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(displayName)
                .setEmailVerified(false);
        return firebaseAuth.createUser(request);
    }

    /**
     * Deletes a user from Firebase Authentication by UID.
     */
    public void deleteFirebaseUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }
}
