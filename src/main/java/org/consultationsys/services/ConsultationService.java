package org.consultationsys.services;

import org.consultationsys.dtos.response.ConsultationResponseDTO;
import org.consultationsys.mappers.ConsultationMapper;
import org.consultationsys.models.*;
import org.consultationsys.models.enums.ConsultationStatus;
import org.consultationsys.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final TechnicalProcedureRepository procedureRepository;
    private static final double BASE_CONSULTATION_FEE = 150.0;

    public ConsultationService() {
        this.consultationRepository = new ConsultationRepository();
        this.patientRepository = new PatientRepository();
        this.userRepository = new UserRepository();
        this.procedureRepository = new TechnicalProcedureRepository();
    }

    public Consultation createConsultation(Long generalistId, Long patientId, String reason,
                                          String description, String observations) {
        Optional<User> userOpt = userRepository.findById(generalistId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Generalist)) {
            throw new IllegalArgumentException("Invalid generalist ID");
        }

        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        Generalist generalist = (Generalist) userOpt.get();
        Patient patient = patientOpt.get();

        Consultation consultation = new Consultation(
            generalist,
            patient,
            reason,
            description,
            observations,
            null, // diagnosis
            null, // treatments
            null  // closingDate
        );

        consultation.setStatus(ConsultationStatus.IN_PROGRESS);
        consultation.setCreationDate(LocalDateTime.now());

        return consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Long consultationId, String observations,
                                          String diagnosis, String treatment) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);

        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Consultation consultation = consultationOpt.get();

        if (observations != null) {
            consultation.setObservations(observations);
        }
        if (diagnosis != null) {
            consultation.setDiagnosis(diagnosis);
        }
        if (treatment != null) {
            consultation.setTreatements(treatment);
        }

        return consultationRepository.update(consultation);
    }

    public Consultation closeConsultation(Long consultationId) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);

        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Consultation consultation = consultationOpt.get();

        if (consultation.getStatus() == ConsultationStatus.COMPLETED) {
            throw new IllegalStateException("Consultation is already completed");
        }

        boolean hasWaitingExpertise = consultation.getExpertiseRequests().stream()
            .anyMatch(er -> er.getStatus() == org.consultationsys.models.enums.ExpertiseRequestStatus.PENDING);

        if (hasWaitingExpertise) {
            throw new IllegalStateException("Cannot close consultation while waiting for specialist opinion");
        }

        consultation.setStatus(ConsultationStatus.COMPLETED);
        consultation.setClosingDate(LocalDateTime.now());

        return consultationRepository.update(consultation);
    }

    public TechnicalProcedure addTechnicalProcedure(Long consultationId, TechnicalProcedure procedure) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);

        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Consultation consultation = consultationOpt.get();
        procedure.setConsultation(consultation);

        return procedureRepository.save(procedure);
    }

    public double calculateTotalCost(Long consultationId) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);

        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Consultation consultation = consultationOpt.get();

        double totalCost = BASE_CONSULTATION_FEE;

        totalCost += consultation.getTechnicalProcedures().stream()
            .mapToDouble(TechnicalProcedure::getCost)
            .sum();

        totalCost += consultation.getExpertiseRequests().stream()
            .filter(er -> er.getSpecialist() != null)
            .mapToDouble(er -> er.getSpecialist().getConsultationFee())
            .sum();

        return totalCost;
    }


    public Optional<Consultation> getConsultationById(Long consultationId) {
        return consultationRepository.findById(consultationId);
    }

    public Optional<ConsultationResponseDTO> getConsultationDTO(Long consultationId) {
        return consultationRepository.findById(consultationId)
            .map(ConsultationMapper::toDTO);
    }

    public List<Consultation> getConsultationsByGeneralist(Long generalistId) {
        return consultationRepository.findByGeneralistId(generalistId);
    }

    public List<Consultation> getConsultationsByPatient(Long patientId) {
        return consultationRepository.findByPatientId(patientId);
    }

    public List<ConsultationResponseDTO> getConsultationsByStatus(ConsultationStatus status) {
        return consultationRepository.findByStatus(status).stream()
            .map(ConsultationMapper::toDTO)
            .collect(Collectors.toList());
    }

    public Consultation markAsWaitingForExpertise(Long consultationId) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);

        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Consultation consultation = consultationOpt.get();
        consultation.setStatus(ConsultationStatus.WAITING_FOR_EXPERTISE);

        return consultationRepository.update(consultation);
    }
}
