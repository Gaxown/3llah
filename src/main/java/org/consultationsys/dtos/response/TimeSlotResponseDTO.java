package org.consultationsys.dtos.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeSlotResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private int duration;
    private boolean available;
    private boolean reserved;
    private Long specialistId;
    private String specialistName;

    public TimeSlotResponseDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    // Helper methods
    public String getFormattedTime() {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFormattedDate() {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDateTime getEndTime() {
        if (dateTime == null) return null;
        return dateTime.plusMinutes(duration);
    }
}

