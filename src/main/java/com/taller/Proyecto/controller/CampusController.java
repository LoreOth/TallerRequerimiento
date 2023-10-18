package com.taller.Proyecto.controller;

import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.dto.RepresentativeRequestDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.repository.CampusRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping("/create/campusFromSpace")
    public ResponseEntity<Campus> createCampusWithObligatorySpace(@RequestBody CampusDto dto) {
        Campus campus = campusService.createCampus(dto);
        return new ResponseEntity<>(campus, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/representatives/{id}/campuses")
    public ResponseEntity<List<CampusDto>> getCampusesByRepresentativeId(@PathVariable Long id) {
        List<Campus> campuses = campusService.findCampusesByRepresentativeId(id);
        return ResponseEntity.ok(mapToDtoList(campuses,null)); 
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{obligatorySpaceId}/sedes")
    public List<CampusDto> getCampusesByObligatorySpaceId(
    	    @PathVariable Long obligatorySpaceId,
    	    @RequestParam Long userId) {
        List<Campus> campuses = campusService.findCampusesByObligatorySpaceId(obligatorySpaceId);
        return mapToDtoList(campuses, userId); // Aquí usas el método de mapeo
    }
    
    
    // "http://localhost:8080/campus/representatives/add"
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/representatives/add")
    public ResponseEntity<?> addRepresentativeToCampus(@RequestBody RepresentativeRequestDto representativeRequest) {
        try {
            campusService.addRepresentativeToCampus(representativeRequest.getCampus_id(), representativeRequest.getUser_id());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Autowired
    private CampusRepository campusRepository;
    public boolean isCampusRepresentedByUser(Long campusId, Long userId) {
        return campusRepository.countByCampusIdAndUserId(campusId, userId) > 0;
    }
    
    
    
    public CampusDto mapToDto(Campus campus, Long userId) {
        CampusDto dto = new CampusDto();
        dto.setId(campus.getId());
        dto.setName(campus.getName());
        dto.setProvince(campus.getProvince());
        dto.setRepresented(isCampusRepresentedByUser(campus.getId(), userId));
        return dto;
    }

    public List<CampusDto> mapToDtoList(List<Campus> campuses, Long userId) {
        List<CampusDto> dtoList = new ArrayList<>();
        for (Campus campus : campuses) {
            dtoList.add(mapToDto(campus , userId));
        }
        return dtoList;
    }
}