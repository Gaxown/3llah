package org.consultationsys.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

import org.consultationsys.models.enums.ConsultationStatus;

public class ConsultationResponseDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long generalistId;
    private String generalistName;
    private String reason;
    private String description;
    private String observations;
    private String diagnosis;
    private String treatment;
    private ConsultationStatus status;
    private Double consultationCost = 150.0; // Fixed consultation fee
    private Double expertiseCost = 0.0;
    private Double proceduresCost = 0.0;
    private Double totalCost;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;

    // Expertise info
    private Boolean hasExpertise;
    private Long expertiseRequestId;
    private String specialistName;
    private String specialty;
    private LocalDateTime expertiseResponseDate;

    // Procedures
    private List<TechnicalProcedureResponseDTO> procedures;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getGeneralistId() {
        return generalistId;
    }

    public void setGeneralistId(Long generalistId) {
        this.generalistId = generalistId;
    }

    public String getGeneralistName() {
        return generalistName;
    }

    public void setGeneralistName(String generalistName) {
        this.generalistName = generalistName;
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

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public Double getConsultationCost() {
        return consultationCost;
    }

    public void setConsultationCost(Double consultationCost) {
        this.consultationCost = consultationCost;
    }

    public Double getExpertiseCost() {
        return expertiseCost;
    }

    public void setExpertiseCost(Double expertiseCost) {
        this.expertiseCost = expertiseCost;
    }

    public Double getProceduresCost() {
        return proceduresCost;
    }

    public void setProceduresCost(Double proceduresCost) {
        this.proceduresCost = proceduresCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public Boolean getHasExpertise() {
        return hasExpertise;
    }

    public void setHasExpertise(Boolean hasExpertise) {
        this.hasExpertise = hasExpertise;
    }

    public Long getExpertiseRequestId() {
        return expertiseRequestId;
    }

    public void setExpertiseRequestId(Long expertiseRequestId) {
        this.expertiseRequestId = expertiseRequestId;
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

    public LocalDateTime getExpertiseResponseDate() {
        return expertiseResponseDate;
    }

    public void setExpertiseResponseDate(LocalDateTime expertiseResponseDate) {
        this.expertiseResponseDate = expertiseResponseDate;
    }

    public List<TechnicalProcedureResponseDTO> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<TechnicalProcedureResponseDTO> procedures) {
        this.procedures = procedures;
    }

    // Helper methods
    public boolean isClosed() {
        return status == ConsultationStatus.COMPLETED;
    }

    public boolean isAwaitingExpertise() {
        return status == ConsultationStatus.PENDING_SPECIALIST_ADVICE;
    }

    public boolean hasProcedures() {
        return procedures != null && !procedures.isEmpty();
    }

    public int getProcedureCount() {
        return procedures != null ? procedures.size() : 0;
    }
}
