package org.consultationsys.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.consultationsys.models.User;
import org.consultationsys.models.enums.Role;
import org.consultationsys.services.AuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AuthController", urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet {
    private AuthenticationService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            handleLogout(request, response);
        } else if ("/login".equals(path)) {
            // Check if already logged in
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                redirectToUserDashboard(user.getRole(), response, request);
                return;
            }

            // Generate CSRF token for login form
            HttpSession newSession = request.getSession(true);
            String csrfToken = generateCsrfToken();
            newSession.setAttribute("csrfToken", csrfToken);

            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            handleLogin(request, response);
        } else if ("/create-test-nurse".equals(path)) {
            createTestNurse(response);
        }
    }

    private void createTestNurse(HttpServletResponse response) throws IOException {
        authService.createTestNurse();
        response.getWriter().println("Test nurse created successfully.");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate CSRF token
        HttpSession session = request.getSession(false);
        String sessionCsrfToken = (session != null) ? (String) session.getAttribute("csrfToken") : null;
        String requestCsrfToken = request.getParameter("csrfToken");

        if (sessionCsrfToken == null || !sessionCsrfToken.equals(requestCsrfToken)) {
            request.setAttribute("error", "Invalid security token. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.setAttribute("email", email);
            generateNewCsrfToken(request);
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        Optional<User> userOpt = authService.authenticate(email, password);

        if (userOpt.isEmpty()) {
            request.setAttribute("error", "Invalid email or password.");
            request.setAttribute("email", email);
            generateNewCsrfToken(request);
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        User user = userOpt.get();

        if (session != null) {
            session.invalidate();
        }
        session = request.getSession(true);

        // Store user in session
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userRole", user.getRole());
        session.setAttribute("userFullName", user.getFullName());

        String newCsrfToken = generateCsrfToken();
        session.setAttribute("csrfToken", newCsrfToken);

        session.setMaxInactiveInterval(30 * 60);

        redirectToUserDashboard(user.getRole(), response, request);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void redirectToUserDashboard(Role role, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String contextPath = request.getContextPath();

        switch (role) {
            case NURSE:
                response.sendRedirect(contextPath + "/nurse/dashboard");
                break;
            case GENERAL_PRACTITIONER:
                response.sendRedirect(contextPath + "/generalist/dashboard");
                break;
            case SPECIALIST:
                response.sendRedirect(contextPath + "/specialist/dashboard");
                break;
            default:
                response.sendRedirect(contextPath + "/login");
                break;
        }
    }

    private String generateCsrfToken() {
        return java.util.UUID.randomUUID().toString();
    }

    private void generateNewCsrfToken(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String csrfToken = generateCsrfToken();
        session.setAttribute("csrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);
    }
}
