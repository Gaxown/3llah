package org.consultationsys.models;

import org.consultationsys.models.enums.ProcedureType;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "technical_procedures")
public class TechnicalProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    Consultation consultation;


    @Column(nullable = false)
    private LocalDate procedureDate;
    @Enumerated(EnumType.STRING)
    private ProcedureType procedureType;
    @Column(nullable = false)
    private double cost;

    public TechnicalProcedure() {
    }

    public TechnicalProcedure(Consultation consultation, LocalDate procedureDate, ProcedureType procedureType, double cost) {
        this.consultation = consultation;
        this.procedureDate = procedureDate;
        this.procedureType = procedureType;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }
    public Consultation getConsultation() {
        return consultation;
    }
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
    public LocalDate getProcedureDate() {
        return procedureDate;
    }
    public void setProcedureDate(LocalDate procedureDate) {
        this.procedureDate = procedureDate;
    }
    public ProcedureType getProcedureType() {
        return procedureType;
    }
    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

}
