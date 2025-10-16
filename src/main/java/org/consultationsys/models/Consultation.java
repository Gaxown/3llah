package org.consultationsys.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.consultationsys.models.enums.ConsultationStatus;

import jakarta.persistence.*;


@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "generalist_id")
    private Generalist generalist;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicalProcedure> technicalProcedures;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpertiseRequest> expertiseRequests;



    @Column(nullable = false, length = 200)
    private String reason;
    @Column(nullable = false, length = 200)
    private String description;
    @Lob
    //private List<String> observations;
    private String observations;
    @Lob
    private String diagnosis;
    // private List<String> treatements;
    @Lob
    private String treatements;
    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;

    public Consultation() {
    }

    public Consultation(Generalist generalist, Patient patient, String reason, String description, String observations, String diagnosis, String treatements, LocalDateTime closingDate) {
        this.generalist = generalist;
        this.patient = patient;
        this.reason = reason;
        this.description = description;
        this.observations = observations;
        this.status = ConsultationStatus.IN_PROGRESS;
        this.creationDate = LocalDateTime.now();
        this.diagnosis = diagnosis;
        this.treatements = treatements;
        this.technicalProcedures = new ArrayList<>();
        this.expertiseRequests = new ArrayList<>();
        this.closingDate = closingDate;
    }

    public Long getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;

    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatements() {
        return treatements;
    }

    public void setTreatements(String treatements) {
        this.treatements = treatements;
    }


    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Generalist getGeneralist() {
        return generalist;
    }

    public void setGeneralist(Generalist generalist) {
        this.generalist = generalist;
    }

    public List<TechnicalProcedure> getTechnicalProcedures() {
        return technicalProcedures;
    }

    public void setTechnicalProcedures(List<TechnicalProcedure> technicalProcedures) {
        this.technicalProcedures = technicalProcedures;
    }

    public List<ExpertiseRequest> getExpertiseRequests() {
        return expertiseRequests;
    }

    public void setExpertiseRequests(List<ExpertiseRequest> expertiseRequests) {
        this.expertiseRequests = expertiseRequests;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
