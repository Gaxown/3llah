package org.consultationsys.dtos.response;

import java.time.LocalDate;
import org.consultationsys.models.enums.ProcedureType;

public class TechnicalProcedureResponseDTO {
    private Long id;
    private ProcedureType procedureType;
    private LocalDate procedureDate;
    private double cost;

    public TechnicalProcedureResponseDTO() {
    }

    public TechnicalProcedureResponseDTO(Long id, ProcedureType procedureType, LocalDate procedureDate, double cost) {
        this.id = id;
        this.procedureType = procedureType;
        this.procedureDate = procedureDate;
        this.cost = cost;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcedureType getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }

    public LocalDate getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(LocalDate procedureDate) {
        this.procedureDate = procedureDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getProcedureTypeDisplay() {
        return procedureType != null ? procedureType.name() : "";
    }
}

