package org.consultationsys.dtos.response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaitingQueueResponseDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private String socialSecurityNumber;
    private LocalDateTime arrivalTime;
    private Integer position;
    private VitalSignResponseDTO latestVitalSigns;

    public WaitingQueueResponseDTO() {
    }

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

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public VitalSignResponseDTO getLatestVitalSigns() {
        return latestVitalSigns;
    }

    public void setLatestVitalSigns(VitalSignResponseDTO latestVitalSigns) {
        this.latestVitalSigns = latestVitalSigns;
    }

    // Helper methods
    public String getFormattedArrivalTime() {
        if (arrivalTime == null) return "";
        return arrivalTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFormattedArrivalDate() {
        if (arrivalTime == null) return "";
        return arrivalTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Long getWaitingTimeMinutes() {
        if (arrivalTime == null) return null;
        return Duration.between(arrivalTime, LocalDateTime.now()).toMinutes();
    }

    public String getWaitingTimeDisplay() {
        Long minutes = getWaitingTimeMinutes();
        if (minutes == null) return "";

        long hours = minutes / 60;
        long mins = minutes % 60;

        if (hours > 0) {
            return String.format("%dh %02dmin", hours, mins);
        }
        return String.format("%d min", mins);
    }
}

