package com.taller.Proyecto.controller;

import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {

    @Autowired
    private CampusService campusService;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<Campus> createCampus(@RequestBody CampusDto dto) {
        Campus campus = campusService.createCampus(dto);
        return new ResponseEntity<>(campus, HttpStatus.CREATED);
    }

    
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{obligatorySpaceId}/sedes")
    public List<CampusDto> getCampusesByObligatorySpaceId(@PathVariable Long obligatorySpaceId) {
        List<Campus> campuses = campusService.findCampusesByObligatorySpaceId(obligatorySpaceId);
        return mapToDtoList(campuses); // Aquí usas el método de mapeo
    }
    
    public CampusDto mapToDto(Campus campus) {
        CampusDto dto = new CampusDto();
        dto.setId(campus.getId());
        dto.setName(campus.getName());
        dto.setProvince(campus.getProvince());
        return dto;
    }

    public List<CampusDto> mapToDtoList(List<Campus> campuses) {
        List<CampusDto> dtoList = new ArrayList<>();
        for (Campus campus : campuses) {
            dtoList.add(mapToDto(campus));
        }
        return dtoList;
    }
}
