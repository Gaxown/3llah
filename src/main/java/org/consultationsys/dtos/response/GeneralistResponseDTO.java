package org.consultationsys.dtos.response;

import java.time.LocalDate;

public class GeneralistResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private boolean active;
    private LocalDate createdAt;
    private int totalConsultations;
    private int pendingConsultations;

    public GeneralistResponseDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(int totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public int getPendingConsultations() {
        return pendingConsultations;
    }

    public void setPendingConsultations(int pendingConsultations) {
        this.pendingConsultations = pendingConsultations;
    }

    // Helper methods
    public boolean hasPendingConsultations() {
        return pendingConsultations > 0;
    }
}
