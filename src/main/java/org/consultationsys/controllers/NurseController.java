package org.consultationsys.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.consultationsys.dtos.response.PatientResponseDTO;
import org.consultationsys.models.Patient;
import org.consultationsys.models.User;
import org.consultationsys.models.VitalSign;
import org.consultationsys.models.enums.Role;
import org.consultationsys.services.PatientService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "NurseController", urlPatterns = {"/nurse/*"})
public class NurseController extends HttpServlet {

    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Role userRole = (Role) session.getAttribute("userRole");
        System.out.println("[DEBUG] NurseController (doGet): Session ID = " + (session != null ? session.getId() : "null"));
        System.out.println("[DEBUG] NurseController (doGet): Retrieved userRole from session = " + userRole);

        // Check authorization - only nurses can access
        if (userRole != Role.NURSE) {
            System.out.println("[DEBUG] NurseController (doGet): Authorization FAILED. Expected role: " + Role.NURSE + ", Actual role: " + userRole);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        System.out.println("[DEBUG] NurseController (doGet): Authorization SUCCEEDED for role: " + userRole);

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
            showDashboard(request, response);
        } else if (pathInfo.equals("/patients/search")) {
            showSearchPage(request, response);
        } else if (pathInfo.equals("/patients/new")) {
            showNewPatientForm(request, response);
        } else if (pathInfo.equals("/patients/list")) {
            showPatientsList(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Role userRole = (Role) session.getAttribute("userRole");
        System.out.println("[DEBUG] NurseController (doPost): Session ID = " + (session != null ? session.getId() : "null"));
        System.out.println("[DEBUG] NurseController (doPost): Retrieved userRole from session = " + userRole);

        // Check authorization
        if (userRole != Role.NURSE) {
            System.out.println("[DEBUG] NurseController (doPost): Authorization FAILED. Expected role: " + Role.NURSE + ", Actual role: " + userRole);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        System.out.println("[DEBUG] NurseController (doPost): Authorization SUCCEEDED for role: " + userRole);

        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/patients/search")) {
            handlePatientSearch(request, response);
        } else if (pathInfo.equals("/patients/create")) {
            handleCreatePatient(request, response);
        } else if (pathInfo.equals("/patients/addVitalSigns")) {
            handleAddVitalSigns(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/nurse/dashboard.jsp").forward(request, response);
    }

    private void showSearchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/nurse/search-patient.jsp").forward(request, response);
    }

    private void showNewPatientForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/nurse/new-patient.jsp").forward(request, response);
    }

    private void showPatientsList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // US2: Get today's patients, sorted by arrival time (Stream API used in service)
        List<PatientResponseDTO> patients = patientService.getTodayPatients();
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/WEB-INF/views/nurse/patients-list.jsp").forward(request, response);
    }

    private void handlePatientSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ssn = request.getParameter("ssn");

        if (ssn == null || ssn.trim().isEmpty()) {
            request.setAttribute("error", "Numéro de sécurité sociale est requis");
            request.getRequestDispatcher("/WEB-INF/views/nurse/search-patient.jsp").forward(request, response);
            return;
        }

        // US1 Step 1: Search patient
        Optional<Patient> patientOpt = patientService.searchPatientBySSN(ssn.trim());

        if (patientOpt.isPresent()) {
            // US1 Step 2a: Patient exists - show info and vital signs form
            Patient patient = patientOpt.get();
            Optional<VitalSign> latestVitalSigns = patientService.getLatestVitalSigns(patient.getId());

            request.setAttribute("patient", patient);
            request.setAttribute("latestVitalSigns", latestVitalSigns.orElse(null));
            request.setAttribute("patientExists", true);
            request.getRequestDispatcher("/WEB-INF/views/nurse/add-vital-signs.jsp").forward(request, response);
        } else {
            // US1 Step 2b: New patient - redirect to creation form
            request.setAttribute("ssn", ssn);
            request.getRequestDispatcher("/WEB-INF/views/nurse/new-patient.jsp").forward(request, response);
        }
    }

    private void handleCreatePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // US1 Step 2b: Create new patient with vital signs
            Patient patient = new Patient();
            patient.setFirstName(request.getParameter("firstName"));
            patient.setLastName(request.getParameter("lastName"));
            patient.setSocialSecurityNumber(request.getParameter("ssn"));
            patient.setPhoneNumber(request.getParameter("phoneNumber"));
            patient.setAddress(request.getParameter("address"));
            patient.setAllergies(request.getParameter("allergies"));

            String dobStr = request.getParameter("dateOfBirth");
            if (dobStr != null && !dobStr.isEmpty()) {
                patient.setDateOfBirth(LocalDate.parse(dobStr));
            }

            Patient savedPatient = patientService.createPatient(patient);

            // Add vital signs
            VitalSign vitalSign = createVitalSignFromRequest(request);
            patientService.addVitalSigns(savedPatient.getId(), vitalSign);

            // US1: Automatically add to queue after registration
            patientService.addToQueue(savedPatient.getId());

            request.getSession().setAttribute("successMessage", "Patient créé et ajouté à la file d'attente avec succès");
            response.sendRedirect(request.getContextPath() + "/nurse/dashboard");

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du patient: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/nurse/new-patient.jsp").forward(request, response);
        }
    }

    private void handleAddVitalSigns(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long patientId = Long.parseLong(request.getParameter("patientId"));

            // US1 Step 2a: Add only new vital signs for existing patient
            VitalSign vitalSign = createVitalSignFromRequest(request);
            patientService.addVitalSigns(patientId, vitalSign);

            // Add to queue
            patientService.addToQueue(patientId);

            request.getSession().setAttribute("successMessage", "Signes vitaux ajoutés et patient ajouté à la file d'attente");
            response.sendRedirect(request.getContextPath() + "/nurse/dashboard");

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'ajout des signes vitaux: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/nurse/patients/search");
        }
    }

    private VitalSign createVitalSignFromRequest(HttpServletRequest request) {
        VitalSign vitalSign = new VitalSign();
        vitalSign.setBloodPressure(Integer.parseInt(request.getParameter("bloodPressure")));
        vitalSign.setHeartRate(Integer.parseInt(request.getParameter("heartRate")));
        vitalSign.setTemperature(Double.parseDouble(request.getParameter("temperature")));
        vitalSign.setRespirationRate(Integer.parseInt(request.getParameter("respirationRate")));
        vitalSign.setWeight(Double.parseDouble(request.getParameter("weight")));
        vitalSign.setHeight(Double.parseDouble(request.getParameter("height")));
        vitalSign.setPulse(Integer.parseInt(request.getParameter("pulse")));
        return vitalSign;
    }
}
