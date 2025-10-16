package org.consultationsys.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.consultationsys.models.enums.Role;

import java.io.IOException;

/**
 * Role-based authorization filter to ensure users only access pages for their role
 */
@WebFilter(filterName = "RoleAuthorizationFilter", urlPatterns = {
    "/nurse/*",
    "/generalist/*",
    "/specialist/*"
})
public class RoleAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("userRole") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        Role userRole = (Role) session.getAttribute("userRole");
        String requestPath = httpRequest.getServletPath();

        // Check if user has permission to access the requested path
        boolean hasPermission = false;

        if (requestPath.startsWith("/nurse/") && userRole == Role.NURSE) {
            hasPermission = true;
        } else if (requestPath.startsWith("/generalist/") && userRole == Role.GENERAL_PRACTITIONER) {
            hasPermission = true;
        } else if (requestPath.startsWith("/specialist/") && userRole == Role.SPECIALIST) {
            hasPermission = true;
        }

        if (hasPermission) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}

