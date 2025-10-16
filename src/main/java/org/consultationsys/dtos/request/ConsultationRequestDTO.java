package org.consultationsys.dtos.request;

import java.util.List;

import org.consultationsys.models.TechnicalProcedure;
import org.consultationsys.models.TimeSlot;
import org.consultationsys.models.enums.Priority;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ConsultationRequestDTO {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Consultation reason is required")
    @Size(max = 500, message = "Reason can have a maximum of 500 characters")
    private String reason;

    @NotNull(message = "Description is required")
    @Size(max = 2000, message = "Description can have a maximum of 2000 characters")
    private String description;

    @Size(max = 2000, message = "Observations can have a maximum of 2000 characters")
    private String observations;

    private String diagnosis;
    private String treatment;

    private Boolean requestExpertise;
    private Long specialistId;
    private Long timeSlotId;
    private String questionForSpecialist;
    private String analysisData;
    private Priority priority = Priority.MEDIUM;

    private List<TechnicalProcedureRequestDTO> procedures;

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Boolean getRequestExpertise() {
        return requestExpertise;
    }

    public void setRequestExpertise(Boolean requestExpertise) {
        this.requestExpertise = requestExpertise;
    }

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getQuestionForSpecialist() {
        return questionForSpecialist;
    }

    public void setQuestionForSpecialist(String question) {
        this.questionForSpecialist = question;
    }

    public String getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(String analysisData) {
        this.analysisData = analysisData;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<TechnicalProcedureRequestDTO> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<TechnicalProcedureRequestDTO> procedures) {
        this.procedures = procedures;
    }

    // Validation Helpers
    public boolean hasExpertiseRequest() {
        return specialistId != null && Boolean.TRUE.equals(requestExpertise);
    }

    public boolean hasProcedures() {
        return procedures != null && !procedures.isEmpty();
    }

    // UI Helper
    public String getPriorityDisplay() {
        return switch (priority) {
            case LOW -> "Low";
            case MEDIUM -> "Medium";
            case HIGH -> "High";
        };
    }
}
