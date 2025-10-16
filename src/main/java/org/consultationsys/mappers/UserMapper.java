package org.consultationsys.mappers;

import org.consultationsys.dtos.response.UserResponseDTO;
import org.consultationsys.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDTO toDTO(User entity) {
        if (entity == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public static List<UserResponseDTO> toDTOList(List<User> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(UserMapper::toDTO)
            .collect(Collectors.toList());
    }
}
