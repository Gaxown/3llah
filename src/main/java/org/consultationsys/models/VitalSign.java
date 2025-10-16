package org.consultationsys.models;


import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "vital_signs")
public class VitalSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;



    @Column(nullable = false)
    private double temperature;
    @Column(nullable = false)
    private int pulse;
    @Column(nullable = false)
    private int respirationRate;
    @Column(nullable = false)
    private int bloodPressure;
    @Column(nullable = false)
    private double weight;
    @Column(nullable = false)
    private double height;
    @Column(nullable = false)
    private Integer heartRate;
    @Column(nullable = false, updatable = false)
    private LocalDateTime measurementDate;

    public VitalSign() {
    }

    public VitalSign(Patient patient, double temperature, int pulse, int respirationRate, int bloodPressure,
            double weight, double height, LocalDateTime measurementDate, Integer heartRate) {
        this.patient = patient;
        this.temperature = temperature;
        this.pulse = pulse;
        this.respirationRate = respirationRate;
        this.bloodPressure = bloodPressure;
        this.weight = weight;
        this.height = height;
        this.measurementDate = measurementDate;
        this.heartRate = heartRate;
    }
    public Long getId() {
        return id;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public int getPulse() {
        return pulse;
    }
    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getRespirationRate() {
        return respirationRate;
    }
    public void setRespirationRate(int respirationRate) {
        this.respirationRate = respirationRate;
    }
    public int getBloodPressure() {
        return bloodPressure;
    }
    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public LocalDateTime getMeasurementDate() {
        return measurementDate;
    }
    public void setMeasurementDate(LocalDateTime measurementDate) {
        this.measurementDate = measurementDate;
    }
    public Integer getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }
}
