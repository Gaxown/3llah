package org.consultationsys.models;
import org.consultationsys.models.enums.ExpertiseRequestStatus;
import org.consultationsys.models.enums.Priority;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "expertise_requests")
public class ExpertiseRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot reservedTimeSlot;



    @Column(nullable = false, length = 200)
    private String reason;
    @Column(nullable = false, length = 200)
    private String questionAsked;
    @Lob
    private String analysisData;
    @Enumerated(EnumType.STRING)
    private ExpertiseRequestStatus status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;
    @Column(nullable = false, updatable = false)
    private LocalDate responseDate;
    @Lob
    private String medicalOpinion;
    @Lob
    private String recommendations;

    public ExpertiseRequest() {
    }

    public ExpertiseRequest(String reason, String questionAsked, String analysisData, ExpertiseRequestStatus status, Priority priority, LocalDate creationDate, LocalDate responseDate, String medicalOpinion, String recommendations) {
        this.reason = reason;
        this.questionAsked = questionAsked;
        this.analysisData = analysisData;
        this.status = status;
        this.priority = priority;
        this.creationDate = creationDate;
        this.responseDate = responseDate;
        this.medicalOpinion = medicalOpinion;
        this.recommendations = recommendations;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public TimeSlot getReservedTimeSlot() {
        return reservedTimeSlot;
    }

    public void setReservedTimeSlot(TimeSlot reservedTimeSlot) {
        this.reservedTimeSlot = reservedTimeSlot;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getQuestionAsked() {
        return questionAsked;
    }

    public void setQuestionAsked(String questionAsked) {
        this.questionAsked = questionAsked;
    }

    public String getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(String analysisData) {
        this.analysisData = analysisData;
    }

    public ExpertiseRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ExpertiseRequestStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public String getMedicalOpinion() {
        return medicalOpinion;
    }

    public void setMedicalOpinion(String medicalOpinion) {
        this.medicalOpinion = medicalOpinion;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
