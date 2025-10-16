package org.consultationsys.dtos.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TimeSlotRequestDTO {

    @NotNull(message = "Specialist ID is required")
    private Long specialistId;

    @NotNull(message = "Date and time is required")
    private LocalDateTime dateTime;

    @NotNull(message = "Duration is required")
    private Integer duration = 30;

    private Boolean available = true;

    private Boolean reserved = false;

    // Getters and Setters
    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    // Helper method
    public boolean isInPast() {
        return dateTime != null && dateTime.isBefore(LocalDateTime.now());
    }
}

