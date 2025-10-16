package org.consultationsys.dtos.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class PatientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private String socialSecurityNumber;
    private String phoneNumber;
    private String address;
    private String allergies;

    // Latest vital signs
    private VitalSignResponseDTO latestVitalSigns;

    // Queue info
    private LocalDateTime arrivalTime;
    private Integer queuePosition;

    public PatientResponseDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public VitalSignResponseDTO getLatestVitalSigns() {
        return latestVitalSigns;
    }

    public void setLatestVitalSigns(VitalSignResponseDTO latestVitalSigns) {
        this.latestVitalSigns = latestVitalSigns;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }

    // Helper methods
    public Integer getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return null;
    }

    public boolean hasAllergies() {
        return allergies != null && !allergies.trim().isEmpty();
    }

    public boolean isInQueue() {
        return arrivalTime != null && queuePosition != null;
    }
}
