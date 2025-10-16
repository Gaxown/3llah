package org.consultationsys.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.consultationsys.models.User;
import org.consultationsys.utils.SessionUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "SpecialistDashboardController", urlPatterns = {"/specialist/dashboard"})
public class SpecialistDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Optional<User> currentUser = SessionUtil.getCurrentUser(request);

        if (currentUser.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("user", currentUser.get());
        request.getRequestDispatcher("/WEB-INF/views/specialist/dashboard.jsp").forward(request, response);
    }
}
