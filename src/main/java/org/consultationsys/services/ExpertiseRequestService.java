package org.consultationsys.services;

import org.consultationsys.dtos.response.ExpertiseRequestResponseDTO;
import org.consultationsys.mappers.ExpertiseRequestMapper;
import org.consultationsys.models.*;
import org.consultationsys.models.enums.ConsultationStatus;
import org.consultationsys.models.enums.ExpertiseRequestStatus;
import org.consultationsys.models.enums.Priority;
import org.consultationsys.repositories.ConsultationRepository;
import org.consultationsys.repositories.ExpertiseRequestRepository;
import org.consultationsys.repositories.TimeSlotRepository;
import org.consultationsys.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpertiseRequestService {

    private final ExpertiseRequestRepository expertiseRequestRepository;
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final SpecialistService specialistService;

    public ExpertiseRequestService() {
        this.expertiseRequestRepository = new ExpertiseRequestRepository();
        this.consultationRepository = new ConsultationRepository();
        this.userRepository = new UserRepository();
        this.timeSlotRepository = new TimeSlotRepository();
        this.specialistService = new SpecialistService();
    }

    public ExpertiseRequest createExpertiseRequest(Long consultationId, int specialistId,
                                                   Long timeSlotId, String reason,
                                                   String questionAsked, String analysisData,
                                                   Priority priority) {
        Optional<Consultation> consultationOpt = consultationRepository.findById(consultationId);
        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + consultationId);
        }

        Optional<User> userOpt = userRepository.findById((long) specialistId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Specialist)) {
            throw new IllegalArgumentException("Specialist not found with ID: " + specialistId);
        }

        Optional<TimeSlot> timeSlotOpt = timeSlotRepository.findById(timeSlotId);
        if (timeSlotOpt.isEmpty()) {
            throw new IllegalArgumentException("Time slot not found with ID: " + timeSlotId);
        }

        Consultation consultation = consultationOpt.get();
        Specialist specialist = (Specialist) userOpt.get();
        TimeSlot timeSlot = timeSlotOpt.get();

        specialistService.reserveTimeSlot(timeSlotId);

        ExpertiseRequest request = new ExpertiseRequest(
            reason,
            questionAsked,
            analysisData,
            ExpertiseRequestStatus.PENDING,
            priority != null ? priority : Priority.MEDIUM,
            LocalDate.now(),
            null, // responseDate
            null, // medicalOpinion
            null  // recommendations
        );

        request.setConsultation(consultation);
        request.setSpecialist(specialist);
        request.setReservedTimeSlot(timeSlot);

        consultation.setStatus(ConsultationStatus.WAITING_FOR_EXPERTISE);
        consultationRepository.update(consultation);

        return expertiseRequestRepository.save(request);
    }

    public ExpertiseRequest respondToRequest(Long requestId, String medicalOpinion,
                                            String recommendations) {
        Optional<ExpertiseRequest> requestOpt = expertiseRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) {
            throw new IllegalArgumentException("Expertise request not found with ID: " + requestId);
        }

        ExpertiseRequest request = requestOpt.get();

        if (request.getStatus() == ExpertiseRequestStatus.COMPLETED) {
            throw new IllegalStateException("This expertise request has already been completed");
        }

        request.setMedicalOpinion(medicalOpinion);
        request.setRecommendations(recommendations);
        request.setStatus(ExpertiseRequestStatus.COMPLETED);
        request.setResponseDate(LocalDate.now());

        Consultation consultation = request.getConsultation();
        consultation.setStatus(ConsultationStatus.IN_PROGRESS);
        consultationRepository.update(consultation);

        return expertiseRequestRepository.update(request);
    }

    public List<ExpertiseRequestResponseDTO> getRequestsBySpecialist(Long specialistId,
                                                                     ExpertiseRequestStatus status) {
        List<ExpertiseRequest> requests = expertiseRequestRepository.findBySpecialistId(specialistId);

        return requests.stream()
            .filter(req -> status == null || req.getStatus() == status)
            .sorted(Comparator.comparing(ExpertiseRequest::getCreationDate).reversed())
            .map(ExpertiseRequestMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<ExpertiseRequestResponseDTO> getRequestsByPriority(Priority priority) {
        return expertiseRequestRepository.findAll().stream()
            .filter(req -> req.getPriority() == priority)
            .filter(req -> req.getStatus() == ExpertiseRequestStatus.PENDING)
            .sorted(Comparator.comparing(ExpertiseRequest::getCreationDate))
            .map(ExpertiseRequestMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<ExpertiseRequestResponseDTO> getAllPendingRequests() {
        return expertiseRequestRepository.findByStatus(ExpertiseRequestStatus.PENDING).stream()
            .sorted(Comparator
                .comparing((ExpertiseRequest req) -> {
                    return switch (req.getPriority()) {
                        case HIGH -> 1;
                        case MEDIUM -> 2;
                        case LOW -> 3;
                    };
                })
                .thenComparing(ExpertiseRequest::getCreationDate))
            .map(ExpertiseRequestMapper::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<ExpertiseRequest> getRequestById(Long requestId) {
        return expertiseRequestRepository.findById(requestId);
    }

    public Optional<ExpertiseRequestResponseDTO> getRequestDTO(Long requestId) {
        return expertiseRequestRepository.findById(requestId)
            .map(ExpertiseRequestMapper::toDTO);
    }

    public List<ExpertiseRequestResponseDTO> getRequestsByConsultation(Long consultationId) {
        return expertiseRequestRepository.findByConsultationId(consultationId).stream()
            .map(ExpertiseRequestMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ExpertiseRequest cancelRequest(Long requestId) {
        Optional<ExpertiseRequest> requestOpt = expertiseRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) {
            throw new IllegalArgumentException("Expertise request not found with ID: " + requestId);
        }

        ExpertiseRequest request = requestOpt.get();

        if (request.getStatus() == ExpertiseRequestStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed expertise request");
        }

        request.setStatus(ExpertiseRequestStatus.CANCELLED);

        TimeSlot timeSlot = request.getReservedTimeSlot();
        timeSlot.setReserved(false);
        timeSlot.setAvailable(true);
        timeSlotRepository.update(timeSlot);

        Consultation consultation = request.getConsultation();
        consultation.setStatus(ConsultationStatus.IN_PROGRESS);
        consultationRepository.update(consultation);

        return expertiseRequestRepository.update(request);
    }
}
