package org.consultationsys.mappers;

import org.consultationsys.dtos.request.TechnicalProcedureRequestDTO;
import org.consultationsys.dtos.response.TechnicalProcedureResponseDTO;
import org.consultationsys.models.TechnicalProcedure;

import java.util.List;
import java.util.stream.Collectors;

public class TechnicalProcedureMapper {

    public static TechnicalProcedure toEntity(TechnicalProcedureRequestDTO dto) {
        if (dto == null) return null;

        TechnicalProcedure entity = new TechnicalProcedure();
        entity.setProcedureType(dto.getProcedureType());
        entity.setProcedureDate(dto.getProcedureDate());
        entity.setCost(dto.getCost());

        return entity;
    }

    public static void updateEntity(TechnicalProcedureRequestDTO dto, TechnicalProcedure entity) {
        if (dto == null || entity == null) return;

        if (dto.getProcedureType() != null) entity.setProcedureType(dto.getProcedureType());
        if (dto.getProcedureDate() != null) entity.setProcedureDate(dto.getProcedureDate());
        if (dto.getCost() != null) entity.setCost(dto.getCost());
    }

    public static TechnicalProcedureResponseDTO toDTO(TechnicalProcedure entity) {
        if (entity == null) return null;

        TechnicalProcedureResponseDTO dto = new TechnicalProcedureResponseDTO();
        dto.setId(entity.getId());
        dto.setProcedureType(entity.getProcedureType());
        dto.setProcedureDate(entity.getProcedureDate());
        dto.setCost(entity.getCost());

        return dto;
    }

    public static List<TechnicalProcedureResponseDTO> toDTOList(List<TechnicalProcedure> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(TechnicalProcedureMapper::toDTO)
            .collect(Collectors.toList());
    }
}
