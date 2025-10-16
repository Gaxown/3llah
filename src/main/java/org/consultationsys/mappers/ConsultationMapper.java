package org.consultationsys.mappers;

import org.consultationsys.dtos.request.ConsultationRequestDTO;
import org.consultationsys.dtos.response.ConsultationResponseDTO;
import org.consultationsys.models.Consultation;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultationMapper {

    public static Consultation toEntity(ConsultationRequestDTO dto) {
        if (dto == null) return null;

        Consultation entity = new Consultation();
        entity.setReason(dto.getReason());
        entity.setDescription(dto.getDescription());
        entity.setObservations(dto.getObservations());
        entity.setDiagnosis(dto.getDiagnosis());
        entity.setTreatements(dto.getTreatment());

        return entity;
    }

    public static void updateEntity(ConsultationRequestDTO dto, Consultation entity) {
        if (dto == null || entity == null) return;

        entity.setReason(dto.getReason());
        entity.setDescription(dto.getDescription());
        entity.setObservations(dto.getObservations());
        entity.setDiagnosis(dto.getDiagnosis());
        entity.setTreatements(dto.getTreatment());
    }

    public static ConsultationResponseDTO toDTO(Consultation entity) {
        if (entity == null) return null;

        ConsultationResponseDTO dto = new ConsultationResponseDTO();
        dto.setId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setDescription(entity.getDescription());
        dto.setObservations(entity.getObservations());
        dto.setDiagnosis(entity.getDiagnosis());
        dto.setTreatment(entity.getTreatements());
        dto.setStatus(entity.getStatus());
        dto.setCreationDate(entity.getCreationDate());
        dto.setClosingDate(entity.getClosingDate());

        // Patient info
        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
            dto.setPatientName(entity.getPatient().getFullName());
        }

        // Generalist info
        if (entity.getGeneralist() != null) {
            dto.setGeneralistId((long) entity.getGeneralist().getId());
            dto.setGeneralistName(entity.getGeneralist().getFullName());
        }

        // Expertise info
        if (entity.getExpertiseRequests() != null && !entity.getExpertiseRequests().isEmpty()) {
            dto.setHasExpertise(true);
            var latestRequest = entity.getExpertiseRequests().get(entity.getExpertiseRequests().size() - 1);
            dto.setExpertiseRequestId(latestRequest.getId());
            if (latestRequest.getSpecialist() != null) {
                dto.setSpecialistName(latestRequest.getSpecialist().getFullName());
                dto.setSpecialty(latestRequest.getSpecialist().getSpecialty());
            }
            if (latestRequest.getResponseDate() != null) {
                dto.setExpertiseResponseDate(latestRequest.getResponseDate().atStartOfDay());
            }
        } else {
            dto.setHasExpertise(false);
        }

        // Map procedures
        if (entity.getTechnicalProcedures() != null) {
            dto.setProcedures(
                entity.getTechnicalProcedures().stream()
                    .map(TechnicalProcedureMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }

        // Calculate costs
        double consultationCost = 150.0; // Fixed consultation fee
        double proceduresCost = 0.0;
        double expertiseCost = 0.0;

        if (entity.getTechnicalProcedures() != null) {
            proceduresCost = entity.getTechnicalProcedures().stream()
                .mapToDouble(p -> p.getCost())
                .sum();
        }

        if (entity.getExpertiseRequests() != null && !entity.getExpertiseRequests().isEmpty()) {
            expertiseCost = entity.getExpertiseRequests().stream()
                .filter(e -> e.getSpecialist() != null)
                .mapToDouble(e -> e.getSpecialist().getConsultationFee())
                .sum();
        }

        dto.setConsultationCost(consultationCost);
        dto.setProceduresCost(proceduresCost);
        dto.setExpertiseCost(expertiseCost);
        dto.setTotalCost(consultationCost + proceduresCost + expertiseCost);

        return dto;
    }

    public static List<ConsultationResponseDTO> toDTOList(List<Consultation> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(ConsultationMapper::toDTO)
            .collect(Collectors.toList());
    }
}
