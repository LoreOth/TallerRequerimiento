package com.taller.Proyecto.controller;

import com.taller.Proyecto.dto.CampusDataDto;
import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.dto.CampusResponseDto;
import com.taller.Proyecto.dto.CampusWithRepresentativeDto;
import com.taller.Proyecto.dto.RepresentativeRequestDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.DEA;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.mappers.CampusMapper;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.service.CampusRepresentativeService;
import com.taller.Proyecto.service.CampusService;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.taller.Proyecto.service.StateSpace;
@RestController
@RequestMapping("/campus")
public class CampusController {

    @Autowired
    private CampusService campusService;
    
    @Autowired
    private CampusRepresentativeService campusRepresnetativeService;
    

    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<CampusResponseDto> createCampus(@RequestBody CampusDto dto) {
        Campus campus = campusService.createCampus(dto);
        CampusResponseDto responseDto = CampusMapper.toDTO(campus);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byProvinces")
    public ResponseEntity<List<CampusWithRepresentativeDto>> getCampusesByProvinces(@RequestParam List<String> provinces) {
        try {
            List<Campus> campuses = campusService.findCampusesByProvincesAndStatus(provinces, 1);  
            List<CampusWithRepresentativeDto> dtos = campusService.mapToDtoList(campuses);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<CampusDataDto> getCampusById(@PathVariable Long id) { 
        try {
            Campus campus = campusService.findCampusById(id);
            if (campus == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            }
            CampusDataDto dto = mapCampusToDto(campus);  
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/approve")
    public ResponseEntity<CampusResponseDto> approveCampus(@RequestBody Map<String, Long> requestBody) {
        Long campusId = requestBody.get("request");
        try {
            Campus approvedCampus = campusService.approveCampus(campusId);
            
            CampusResponseDto responseDto = CampusMapper.toDTO(approvedCampus);
            
            campusService.updateObligatorySpacesStatus(approvedCampus, true);
            
            
            
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/updateCampusStatus")
    public ResponseEntity<?> updateCampusStatus(
            @RequestParam Long campusId,
            @RequestParam Integer status) {
        try {
            campusService.updateCampusStatus(campusId, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/updateCampusData")
    public ResponseEntity<?> updateCampusData(@RequestBody CampusDataDto campusDataDto) {
        try {
            campusService.updateCampus(campusDataDto);  
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/deaCount")
    public ResponseEntity<Integer> getDeaCountByCampusId(@PathVariable Long id) {
        try {
            int count = campusService.countDeasByCampusId(id);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CampusDataDto mapCampusToDto(Campus campus) {
        CampusDataDto dto = new CampusDataDto();
        dto.setId(campus.getId());
        dto.setLatitude(campus.getLatitude());
        dto.setLongitude(campus.getLongitude());
        dto.setCity(campus.getCity());
        dto.setAddress(campus.getAddress());
        dto.setProvince(campus.getProvince());
        dto.setName(campus.getName());
        dto.setCuit(campus.getCuit());
        dto.setArea(campus.getArea());
        dto.setFloors(campus.getFloors());
        dto.setPermanentStaff(campus.getPermanentStaff());
        dto.setAverageVisits(campus.getAverageVisits());
        return dto;
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
        return ResponseEntity.ok(mapToDtoList(campuses,id)); 
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{obligatorySpaceId}/sedes")
    public List<CampusDto> getCampusesByObligatorySpaceId(
    	    @PathVariable Long obligatorySpaceId,
    	    @RequestParam Long userId) {
        List<Campus> campuses = campusService.findCampusesByObligatorySpaceId(obligatorySpaceId);
        return mapToDtoList(campuses, userId); 
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
        dto.setStatus(campus.getStatus());
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