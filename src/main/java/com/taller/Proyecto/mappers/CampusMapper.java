package com.taller.Proyecto.mappers;

import java.util.List;

import com.taller.Proyecto.dto.CampusResponseDto;
import com.taller.Proyecto.dto.CampusWithRepresentativeDto;
import com.taller.Proyecto.dto.representativeResponseDTO;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.DEA;

public class CampusMapper {
    public static CampusResponseDto toDTO(Campus campus) {
        CampusResponseDto dto = new CampusResponseDto();
        dto.setId(campus.getId());
        dto.setProvince(campus.getProvince());
        dto.setName(campus.getName());
        dto.setCuit(campus.getCuit());
        dto.setStatus(campus.getStatus());
  
        
        return dto;
    }
}
