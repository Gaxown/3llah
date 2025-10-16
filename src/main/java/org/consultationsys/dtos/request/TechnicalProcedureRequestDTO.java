package org.consultationsys.dtos.request;

import org.consultationsys.models.enums.ProcedureType;
import java.time.LocalDate;

public class TechnicalProcedureRequestDTO {

    private Long consultationId;
    private ProcedureType procedureType;
    private LocalDate procedureDate;
    private Double cost;

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
