package org.consultationsys.mappers;

import org.consultationsys.dtos.request.VitalSignRequestDTO;
import org.consultationsys.dtos.response.VitalSignResponseDTO;
import org.consultationsys.models.VitalSign;

import java.util.List;
import java.util.stream.Collectors;

public class VitalSignMapper {

    public static VitalSign toEntity(VitalSignRequestDTO dto) {
        if (dto == null) return null;

        VitalSign entity = new VitalSign();
        entity.setTemperature(dto.getTemperature());
        entity.setPulse(dto.getPulse());
        entity.setHeartRate(dto.getHeartRate());
        entity.setRespirationRate(dto.getRespirationRate());
        entity.setBloodPressure(dto.getBloodPressure());
        entity.setWeight(dto.getWeight());
        entity.setHeight(dto.getHeight());

        return entity;
    }

    public static VitalSignResponseDTO toDTO(VitalSign entity) {
        if (entity == null) return null;

        VitalSignResponseDTO dto = new VitalSignResponseDTO();
        dto.setId(entity.getId());
        dto.setTemperature(entity.getTemperature());
        dto.setPulse(entity.getPulse());
        dto.setHeartRate(entity.getHeartRate());
        dto.setRespirationRate(entity.getRespirationRate());
        dto.setBloodPressure(entity.getBloodPressure());
        dto.setWeight(entity.getWeight());
        dto.setHeight(entity.getHeight());
        dto.setMeasurementDate(entity.getMeasurementDate());

        return dto;
    }

    public static List<VitalSignResponseDTO> toDTOList(List<VitalSign> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(VitalSignMapper::toDTO)
            .collect(Collectors.toList());
    }
}
