package org.consultationsys.mappers;

import org.consultationsys.dtos.request.SpecialistRequestDTO;
import org.consultationsys.dtos.response.SpecialistResponseDTO;
import org.consultationsys.models.Specialist;
import org.consultationsys.models.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

public class SpecialistMapper {

    public static Specialist toEntity(SpecialistRequestDTO dto) {
        if (dto == null) return null;

        Specialist entity = new Specialist();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setSpecialty(dto.getSpecialty());
        entity.setConsultationFee(dto.getConsultationFee());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);

        return entity;
    }

    public static void updateEntity(SpecialistRequestDTO dto, Specialist entity) {
        if (dto == null || entity == null) return;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setSpecialty(dto.getSpecialty());
        entity.setConsultationFee(dto.getConsultationFee());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
    }

    public static SpecialistResponseDTO toDTO(Specialist entity) {
        if (entity == null) return null;

        SpecialistResponseDTO dto = new SpecialistResponseDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setSpecialty(entity.getSpecialty());
        dto.setConsultationFee(entity.getConsultationFee());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());

        // Count available time slots if needed
        // This will be set by the service layer when needed
        dto.setAvailableSlots(0);

        return dto;
    }

    public static List<SpecialistResponseDTO> toDTOList(List<Specialist> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(SpecialistMapper::toDTO)
            .collect(Collectors.toList());
    }
}
