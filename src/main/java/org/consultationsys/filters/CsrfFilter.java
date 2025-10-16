package org.consultationsys.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * CSRF protection filter for state-changing operations
 */
@WebFilter(filterName = "CsrfFilter", urlPatterns = {"/*"})
public class CsrfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();

        // Only check CSRF token for state-changing methods
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) ||
            "DELETE".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) {

            // Skip CSRF check for login endpoint (CSRF token is validated in the controller)
            String servletPath = httpRequest.getServletPath();
            if ("/login".equals(servletPath)) {
                chain.doFilter(request, response);
                return;
            }

            HttpSession session = httpRequest.getSession(false);

            if (session == null) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "No session found");
                return;
            }

            String sessionToken = (String) session.getAttribute("csrfToken");
            String requestToken = httpRequest.getParameter("csrfToken");

            // Also check header for AJAX requests
            if (requestToken == null) {
                requestToken = httpRequest.getHeader("X-CSRF-Token");
            }

            if (sessionToken == null || !sessionToken.equals(requestToken)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}

