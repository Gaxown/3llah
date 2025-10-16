package org.consultationsys.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Authentication filter to protect routes requiring login
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/nurse/*",
    "/generalist/*",
    "/specialist/*",
    "/patients/*",
    "/consultations/*",
    "/expertise/*",
    "/timeslots/*"
})
public class AuthFilter implements Filter {

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

        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            // User is authenticated, proceed with request
            chain.doFilter(request, response);
        } else {
            // User is not authenticated, redirect to login
            String contextPath = httpRequest.getContextPath();
            httpResponse.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}

