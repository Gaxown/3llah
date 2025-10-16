package org.consultationsys.mappers;

import org.consultationsys.dtos.request.WaitingQueueRequestDTO;
import org.consultationsys.dtos.response.WaitingQueueResponseDTO;
import org.consultationsys.models.WaitingQueue;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WaitingQueueMapper {

    public static WaitingQueue toEntity(WaitingQueueRequestDTO dto) {
        if (dto == null) return null;

        WaitingQueue entity = new WaitingQueue();
        // Patient will be set by the service layer
        // arrivalTime will be set automatically

        return entity;
    }

    public static WaitingQueueResponseDTO toDTO(WaitingQueue entity) {
        if (entity == null) return null;

        WaitingQueueResponseDTO dto = new WaitingQueueResponseDTO();
        dto.setId(entity.getId());
        dto.setArrivalTime(entity.getArrivalTime());

        // Patient info
        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
            dto.setPatientName(entity.getPatient().getFullName());
            dto.setSocialSecurityNumber(entity.getPatient().getSocialSecurityNumber());

            // Get latest vital signs
            if (entity.getPatient().getVitalSigns() != null && !entity.getPatient().getVitalSigns().isEmpty()) {
                var latestVitalSign = entity.getPatient().getVitalSigns().stream()
                    .max(Comparator.comparing(vs -> vs.getMeasurementDate()))
                    .orElse(null);
                if (latestVitalSign != null) {
                    dto.setLatestVitalSigns(VitalSignMapper.toDTO(latestVitalSign));
                }
            }
        }

        return dto;
    }

    public static List<WaitingQueueResponseDTO> toDTOList(List<WaitingQueue> entities) {
        if (entities == null) return null;

        // Sort by arrival time and assign positions
        List<WaitingQueue> sorted = entities.stream()
            .sorted(Comparator.comparing(WaitingQueue::getArrivalTime))
            .collect(Collectors.toList());

        List<WaitingQueueResponseDTO> dtos = sorted.stream()
            .map(WaitingQueueMapper::toDTO)
            .collect(Collectors.toList());

        // Assign queue positions
        for (int i = 0; i < dtos.size(); i++) {
            dtos.get(i).setPosition(i + 1);
        }

        return dtos;
    }
}
