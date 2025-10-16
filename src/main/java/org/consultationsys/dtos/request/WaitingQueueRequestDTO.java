package org.consultationsys.dtos.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WaitingQueueRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private LocalDateTime arrivalTime;

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Helper method - if arrival time not set, use current time
    public LocalDateTime getEffectiveArrivalTime() {
        return arrivalTime != null ? arrivalTime : LocalDateTime.now();
    }
}

