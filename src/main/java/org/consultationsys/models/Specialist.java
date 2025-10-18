package org.consultationsys.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.consultationsys.models.enums.Role;


@Entity
@DiscriminatorValue("SPECIALIST")

public class Specialist extends User {


    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpertiseRequest> expertiseRequests;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots;


    @Column(nullable = true, length = 100)
    private String specialty;
    @Column(nullable = true)
    private double consultationFee;


    public Specialist() {
        super();
        this.expertiseRequests = new ArrayList<>();
        this.timeSlots = new ArrayList<>();
    }

    public Specialist(String lastName, String firstName, String email, String passwordHash, boolean active, String specialty, double consultationFee) {
        super(lastName, firstName, email, passwordHash, active);
        this.specialty = specialty;
        this.consultationFee = consultationFee;
        this.expertiseRequests = new ArrayList<>();
        this.timeSlots = new ArrayList<>();
    }

    public Long getId() {
        return super.getId();
    }

    public String getSpecialty() {
        return specialty;
    }


    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
