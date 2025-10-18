package org.consultationsys.models;

import org.consultationsys.models.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "generalist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations;

    @Column(nullable = false, length = 50)
    String lastName;
    @Column(nullable = false, length = 50)
    String firstName;
    @Column(nullable = false, unique = true, length = 50)
    String email;
    @Column(nullable = false, length = 60)
    String passwordHash;
    @Column(nullable = false)
    boolean active;
    @Column(name="created_at", nullable = false, updatable = false)
    LocalDate createdAt;

    protected User() {
        this.createdAt = LocalDate.now();
    }

    protected User(String lastName, String firstName, String email, String passwordHash, boolean active) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.consultations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Get the role based on the discriminator (subclass type)
     * @return Role enum value
     */
    public Role getRole() {
        if (this instanceof Nurse) {
            return Role.NURSE;
        } else if (this instanceof Generalist) {
            return Role.GENERAL_PRACTITIONER;
        } else if (this instanceof Specialist) {
            return Role.SPECIALIST;
        }
        return null;
    }

};
