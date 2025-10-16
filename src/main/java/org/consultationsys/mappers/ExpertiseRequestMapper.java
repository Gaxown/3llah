package org.consultationsys.mappers;

import org.consultationsys.dtos.request.ExpertiseRequestDTO;
import org.consultationsys.dtos.response.ExpertiseRequestResponseDTO;
import org.consultationsys.models.ExpertiseRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ExpertiseRequestMapper {

    public static ExpertiseRequest toEntity(ExpertiseRequestDTO dto) {
        if (dto == null) return null;

        ExpertiseRequest entity = new ExpertiseRequest();
        entity.setReason(dto.getReason());
        entity.setQuestionAsked(dto.getQuestionAsked());
        entity.setAnalysisData(dto.getAnalysisData());
        entity.setPriority(dto.getPriority());
        entity.setStatus(dto.getStatus());
        entity.setMedicalOpinion(dto.getMedicalOpinion());
        entity.setRecommendations(dto.getRecommendations());

        return entity;
    }

    public static void updateEntity(ExpertiseRequestDTO dto, ExpertiseRequest entity) {
        if (dto == null || entity == null) return;

        if (dto.getReason() != null) entity.setReason(dto.getReason());
        if (dto.getQuestionAsked() != null) entity.setQuestionAsked(dto.getQuestionAsked());
        if (dto.getAnalysisData() != null) entity.setAnalysisData(dto.getAnalysisData());
        if (dto.getPriority() != null) entity.setPriority(dto.getPriority());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getMedicalOpinion() != null) entity.setMedicalOpinion(dto.getMedicalOpinion());
        if (dto.getRecommendations() != null) entity.setRecommendations(dto.getRecommendations());
    }

    public static ExpertiseRequestResponseDTO toDTO(ExpertiseRequest entity) {
        if (entity == null) return null;

        ExpertiseRequestResponseDTO dto = new ExpertiseRequestResponseDTO();
        dto.setId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setQuestionAsked(entity.getQuestionAsked());
        dto.setAnalysisData(entity.getAnalysisData());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setCreationDate(entity.getCreationDate());
        dto.setResponseDate(entity.getResponseDate());
        dto.setMedicalOpinion(entity.getMedicalOpinion());
        dto.setRecommendations(entity.getRecommendations());

        // Consultation info
        if (entity.getConsultation() != null) {
            dto.setConsultationId(entity.getConsultation().getId());

            if (entity.getConsultation().getPatient() != null) {
                dto.setPatientName(entity.getConsultation().getPatient().getFullName());
            }

            if (entity.getConsultation().getGeneralist() != null) {
                dto.setGeneralistName(entity.getConsultation().getGeneralist().getFullName());
            }
        }

        // Specialist info
        if (entity.getSpecialist() != null) {
            dto.setSpecialistName(entity.getSpecialist().getFullName());
            dto.setSpecialty(entity.getSpecialist().getSpecialty());
        }

        // Time slot info
        if (entity.getReservedTimeSlot() != null) {
            dto.setTimeSlot(TimeSlotMapper.toDTO(entity.getReservedTimeSlot()));
        }

        return dto;
    }

    public static List<ExpertiseRequestResponseDTO> toDTOList(List<ExpertiseRequest> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(ExpertiseRequestMapper::toDTO)
            .collect(Collectors.toList());
    }
}
