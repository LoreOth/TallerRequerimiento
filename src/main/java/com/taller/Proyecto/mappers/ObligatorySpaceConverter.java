package com.taller.Proyecto.mappers;

import com.taller.Proyecto.dto.ObligatorySpaceResponseDto;
import com.taller.Proyecto.entity.ObligatorySpace;

public class ObligatorySpaceConverter {
	public static ObligatorySpaceResponseDto toDTO(ObligatorySpace entity) {
		ObligatorySpaceResponseDto dto = new ObligatorySpaceResponseDto();
        dto.setId(entity.getId());
        dto.setProvince(entity.getProvince());
        dto.setName(entity.getName());
        dto.setStatus(entity.isStatus());
        dto.setCUIT(entity.getCUIT());

        return dto;
    }
}
