package org.consultationsys.dtos.response;

import java.time.LocalDate;
import org.consultationsys.models.enums.Role;

public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Role role;
    private boolean active;
    private LocalDate createdAt;

    public UserResponseDTO() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    // Helper methods
    public String getRoleDisplay() {
        return switch (role) {
            case NURSE -> "Infirmier(ère)";
            case GENERAL_PRACTITIONER -> "Médecin Généraliste";
            case SPECIALIST -> "Médecin Spécialiste";
            default -> "Utilisateur";
        };
    }

    public boolean isNurse() {
        return role == Role.NURSE;
    }

    public boolean isGeneralist() {
        return role == Role.GENERAL_PRACTITIONER;
    }

    public boolean isSpecialist() {
        return role == Role.SPECIALIST;
    }
}
