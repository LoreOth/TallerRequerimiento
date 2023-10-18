package com.taller.Proyecto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.taller.Proyecto.dto.UserResponseDto;
import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.User;

public class UserMapper {
    public static UserResponseDto toDTO(User user) {
        UserResponseDto dto = new UserResponseDto();
        
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setProvince(user.getProvince());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName) 
                .collect(Collectors.toList());
dto.setRoles(roleNames);
        return dto;
    }
}
