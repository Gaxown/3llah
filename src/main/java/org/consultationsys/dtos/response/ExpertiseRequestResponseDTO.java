package org.consultationsys.dtos.response;

import java.time.LocalDate;
import org.consultationsys.models.enums.ExpertiseRequestStatus;
import org.consultationsys.models.enums.Priority;

public class ExpertiseRequestResponseDTO {
    private Long id;
    private Long consultationId;
    private String patientName;
    private String generalistName;
    private String specialistName;
    private String specialty;
    private String reason;
    private String questionAsked;
    private String analysisData;
    private ExpertiseRequestStatus status;
    private Priority priority;
    private LocalDate creationDate;
    private LocalDate responseDate;
    private String medicalOpinion;
    private String recommendations;
    private TimeSlotResponseDTO timeSlot;

    public ExpertiseRequestResponseDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGeneralistName() {
        return generalistName;
    }

    public void setGeneralistName(String generalistName) {
        this.generalistName = generalistName;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getQuestionAsked() {
        return questionAsked;
    }

    public void setQuestionAsked(String questionAsked) {
        this.questionAsked = questionAsked;
    }

    public String getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(String analysisData) {
        this.analysisData = analysisData;
    }

    public ExpertiseRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ExpertiseRequestStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public String getMedicalOpinion() {
        return medicalOpinion;
    }

    public void setMedicalOpinion(String medicalOpinion) {
        this.medicalOpinion = medicalOpinion;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public TimeSlotResponseDTO getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotResponseDTO timeSlot) {
        this.timeSlot = timeSlot;
    }

    // Helper methods
    public boolean isCompleted() {
        return status == ExpertiseRequestStatus.COMPLETED;
    }

    public boolean isPending() {
        return status == ExpertiseRequestStatus.PENDING;
    }

    public boolean isUrgent() {
        return priority == Priority.HIGH;
    }

    public String getPriorityDisplay() {
        return switch (priority) {
            case LOW -> "Basse";
            case MEDIUM -> "Normale";
            case HIGH -> "Urgente";
        };
    }

    public String getStatusDisplay() {
        return switch (status) {
            case PENDING -> "En attente";
            case COMPLETED -> "Terminée";
            case CANCELLED -> "Annulée";
            default -> "Inconnu";
        };
    }
}
