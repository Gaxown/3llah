package org.consultationsys.mappers;

import org.consultationsys.dtos.request.GeneralistRequestDTO;
import org.consultationsys.dtos.response.GeneralistResponseDTO;
import org.consultationsys.models.Generalist;
import org.consultationsys.models.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralistMapper {

    public static Generalist toEntity(GeneralistRequestDTO dto) {
        if (dto == null) return null;

        Generalist entity = new Generalist();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);

        return entity;
    }

    public static void updateEntity(GeneralistRequestDTO dto, Generalist entity) {
        if (dto == null || entity == null) return;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
    }

    public static GeneralistResponseDTO toDTO(Generalist entity) {
        if (entity == null) return null;

        GeneralistResponseDTO dto = new GeneralistResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());

        // Statistics will be set by service layer if needed
        dto.setTotalConsultations(0);
        dto.setPendingConsultations(0);

        return dto;
    }

    public static List<GeneralistResponseDTO> toDTOList(List<Generalist> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(GeneralistMapper::toDTO)
            .collect(Collectors.toList());
    }
}
