package org.consultationsys.mappers;

import org.consultationsys.dtos.request.PatientRequestDTO;
import org.consultationsys.dtos.response.PatientResponseDTO;
import org.consultationsys.models.Patient;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {

    public static Patient toEntity(PatientRequestDTO dto) {
        if (dto == null) return null;

        Patient entity = new Patient();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());
        entity.setAllergies(dto.getAllergies());

        return entity;
    }

    public static void updateEntity(PatientRequestDTO dto, Patient entity) {
        if (dto == null || entity == null) return;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());
        entity.setAllergies(dto.getAllergies());
    }

    public static PatientResponseDTO toDTO(Patient entity) {
        if (entity == null) return null;

        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setSocialSecurityNumber(entity.getSocialSecurityNumber());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        dto.setAllergies(entity.getAllergies());

        // Get latest vital signs
        if (entity.getVitalSigns() != null && !entity.getVitalSigns().isEmpty()) {
            var latestVitalSign = entity.getVitalSigns().stream()
                .max(Comparator.comparing(vs -> vs.getMeasurementDate()))
                .orElse(null);
            if (latestVitalSign != null) {
                dto.setLatestVitalSigns(VitalSignMapper.toDTO(latestVitalSign));
            }
        }

        // Get queue info
        if (entity.getWaitingQueues() != null && !entity.getWaitingQueues().isEmpty()) {
            var latestQueue = entity.getWaitingQueues().stream()
                .max(Comparator.comparing(wq -> wq.getArrivalTime()))
                .orElse(null);
            if (latestQueue != null) {
                dto.setArrivalTime(latestQueue.getArrivalTime());
            }
        }

        return dto;
    }

    public static List<PatientResponseDTO> toDTOList(List<Patient> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(PatientMapper::toDTO)
            .collect(Collectors.toList());
    }
}
