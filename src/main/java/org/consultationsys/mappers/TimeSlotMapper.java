package org.consultationsys.mappers;

import org.consultationsys.dtos.request.TimeSlotRequestDTO;
import org.consultationsys.dtos.response.TimeSlotResponseDTO;
import org.consultationsys.models.TimeSlot;

import java.util.List;
import java.util.stream.Collectors;

public class TimeSlotMapper {

    public static TimeSlot toEntity(TimeSlotRequestDTO dto) {
        if (dto == null) return null;

        TimeSlot entity = new TimeSlot();
        entity.setDateTime(dto.getDateTime());
        entity.setDuration(dto.getDuration() != null ? dto.getDuration() : 30);
        entity.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        entity.setReserved(dto.getReserved() != null ? dto.getReserved() : false);

        return entity;
    }

    public static void updateEntity(TimeSlotRequestDTO dto, TimeSlot entity) {
        if (dto == null || entity == null) return;

        if (dto.getDateTime() != null) entity.setDateTime(dto.getDateTime());
        if (dto.getDuration() != null) entity.setDuration(dto.getDuration());
        if (dto.getAvailable() != null) entity.setAvailable(dto.getAvailable());
        if (dto.getReserved() != null) entity.setReserved(dto.getReserved());
    }

    public static TimeSlotResponseDTO toDTO(TimeSlot entity) {
        if (entity == null) return null;

        TimeSlotResponseDTO dto = new TimeSlotResponseDTO();
        dto.setId(entity.getId());
        dto.setDateTime(entity.getDateTime());
        dto.setDuration(entity.getDuration());
        dto.setAvailable(entity.isAvailable());
        dto.setReserved(entity.isReserved());

        if (entity.getSpecialist() != null) {
            dto.setSpecialistId(entity.getSpecialist().getId());
            dto.setSpecialistName(entity.getSpecialist().getFullName());
        }

        return dto;
    }

    public static List<TimeSlotResponseDTO> toDTOList(List<TimeSlot> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(TimeSlotMapper::toDTO)
            .collect(Collectors.toList());
    }
}
