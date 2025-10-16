package org.consultationsys.services;

import org.consultationsys.models.User;
import org.consultationsys.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService() {
        this.userRepository = new UserRepository();
    }

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticate a user with email and password
     * @param email User's email
     * @param password Plain text password
     * @return Optional<User> if authentication successful
     */
    public Optional<User> authenticate(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findByEmail(email.trim().toLowerCase());

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // Check if user is active
        if (!user.isActive()) {
            return Optional.empty();
        }

        // Verify password using BCrypt
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    /**
     * Hash a password using BCrypt
     * @param plainPassword Plain text password
     * @return Hashed password
     */
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    /**
     * Verify if a plain password matches a hashed password
     * @param plainPassword Plain text password
     * @param hashedPassword BCrypt hashed password
     * @return true if passwords match
     */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Change user password
     * @param userId User ID
     * @param oldPassword Current password
     * @param newPassword New password
     * @return true if password changed successfully
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        // Verify old password
        if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
            return false;
        }

        // Hash and set new password
        user.setPasswordHash(hashPassword(newPassword));
        userRepository.update(user);

        return true;
    }
}

