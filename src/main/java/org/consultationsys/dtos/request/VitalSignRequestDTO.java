package org.consultationsys.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class VitalSignRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Temperature is required")
    @Positive(message = "Temperature must be positive")
    private Double temperature;

    @NotNull(message = "Pulse is required")
    @Positive(message = "Pulse must be positive")
    private Integer pulse;

    @NotNull(message = "Respiration rate is required")
    @Positive(message = "Respiration rate must be positive")
    private Integer respirationRate;

    @NotNull(message = "Blood pressure is required")
    @Positive(message = "Blood pressure must be positive")
    private Integer bloodPressure;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    @NotNull(message = "Heart rate is required")
    @Positive(message = "Heart rate must be positive")
    private Integer heartRate;

    private LocalDateTime measurementDate;

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(Integer respirationRate) {
        this.respirationRate = respirationRate;
    }

    public Integer getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(Integer bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public LocalDateTime getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDateTime measurementDate) {
        this.measurementDate = measurementDate;
    }
}
