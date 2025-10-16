package org.consultationsys.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.consultationsys.models.User;
import org.consultationsys.models.enums.Role;

import java.util.Optional;

/**
 * Utility class for session management
 */
public class SessionUtil {

    /**
     * Get the current logged-in user from session
     */
    public static Optional<User> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        User user = (User) session.getAttribute("user");
        return Optional.ofNullable(user);
    }

    /**
     * Get the current user's role
     */
    public static Optional<Role> getCurrentUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        Role role = (Role) session.getAttribute("userRole");
        return Optional.ofNullable(role);
    }

    /**
     * Check if user is logged in
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    /**
     * Check if current user has a specific role
     */
    public static boolean hasRole(HttpServletRequest request, Role role) {
        return getCurrentUserRole(request)
                .map(userRole -> userRole == role)
                .orElse(false);
    }

    /**
     * Get CSRF token from session
     */
    public static String getCsrfToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute("csrfToken");
    }

    /**
     * Invalidate current session
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}

