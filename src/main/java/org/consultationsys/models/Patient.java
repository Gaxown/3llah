package org.consultationsys.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Consultation> Consultations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VitalSign> vitalSigns;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WaitingQueue> waitingQueues;




    @Column(nullable = true, unique = true)
    private String phoneNumber;
    @Column(nullable = true)
    private String address;
    private LocalDate dateOfBirth;
    @Column(nullable = false, unique = true)
    private String socialSecurityNumber;
    @Column(nullable = false)
    private String allergies;

    public Patient() {
        this.Consultations = new ArrayList<>();
        this.vitalSigns = new ArrayList<>();
        this.waitingQueues = new ArrayList<>();
    }

    public Patient(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth, String socialSecurityNumber, String allergies) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.allergies = allergies;
    }

    // Getters and Setters
    public Long getId() {
        return id;
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
        return firstName + " " + lastName;
    }

    public List<Consultation> getConsultations() {
        return Consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.Consultations = consultations;
    }

    public List<VitalSign> getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(List<VitalSign> vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public List<WaitingQueue> getWaitingQueues() {
        return waitingQueues;
    }

    public void setWaitingQueues(List<WaitingQueue> waitingQueues) {
        this.waitingQueues = waitingQueues;
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

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
