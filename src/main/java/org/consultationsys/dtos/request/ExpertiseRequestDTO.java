package org.consultationsys.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.consultationsys.models.enums.ExpertiseRequestStatus;
import org.consultationsys.models.enums.Priority;

public class ExpertiseRequestDTO {

    @NotNull(message = "Consultation ID is required")
    private Long consultationId;

    @NotNull(message = "Specialist ID is required")
    private Long specialistId;

    @NotNull(message = "Time slot ID is required")
    private Long timeSlotId;

    @NotBlank(message = "Reason is required")
    @Size(max = 200, message = "Reason cannot exceed 200 characters")
    private String reason;

    @NotBlank(message = "Question is required")
    @Size(max = 200, message = "Question cannot exceed 200 characters")
    private String questionAsked;

    @Size(max = 2000, message = "Analysis data cannot exceed 2000 characters")
    private String analysisData;

    @NotNull(message = "Priority is required")
    private Priority priority;

    private ExpertiseRequestStatus status;

    // For specialist response
    @Size(max = 2000, message = "Medical opinion cannot exceed 2000 characters")
    private String medicalOpinion;

    @Size(max = 2000, message = "Recommendations cannot exceed 2000 characters")
    private String recommendations;

    // Getters and Setters
    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ExpertiseRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ExpertiseRequestStatus status) {
        this.status = status;
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
}

