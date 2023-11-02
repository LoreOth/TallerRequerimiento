package com.taller.Proyecto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.SwornDeclarationResponseDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.SwornDeclarationRepository;
import com.taller.Proyecto.service.CampusService;
import com.taller.Proyecto.service.SwornDeclarationService;

@RestController
@RequestMapping("/documentation")
public class SwornDeclarationController {

    @Autowired
    private SwornDeclarationService service;
    
    @Autowired
    private SwornDeclarationRepository repository;
    
    @Autowired
    private CampusRepository campusRepository;
    
    @Autowired
    private CampusService campusService;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/SwornDeclarationSave")
    public SwornDeclaration saveDeclaration(@RequestBody SwornDeclaration declaration) {
    	boolean status =false;
        if (campusService.shouldUpdateStatusToZero(declaration)) { // si cumple con todo ok y deas coincidentes
            campusService.updateCampusStatus(declaration.getCampusId(), 0);
            status =true;
        }
        SwornDeclaration savedDeclaration = service.saveOrUpdateDeclaration(declaration,status);
        return savedDeclaration;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/pendingDeclarations")
    public List<SwornDeclarationResponseDto> getPendingDeclarations(@RequestParam("provinces") List<String> selectedProvinces) {
        List<SwornDeclaration> pendingDeclarations = repository.findByPendingTrueAndCampusProvinceIn(selectedProvinces);

        List<SwornDeclarationResponseDto> responseDtos = new ArrayList<>();
        for (SwornDeclaration swornDeclaration : pendingDeclarations) {
            SwornDeclarationResponseDto responseDto = mapSwornDeclarationToResponseDto(swornDeclaration);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/updateDeclarationStatus")
    public void updateDeclarationStatus(@RequestBody SwornDeclaration declaration) {
    	service.updateVisitStatus(declaration, true);
        service.updateDeclaration(declaration, false);
        campusService.updateCampusStatus(declaration.getCampusId(), 2);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/rejectDeclarationStatus")
    public void rejectDeclarationStatus(@RequestBody SwornDeclaration declaration) {
        service.updateVisitStatus(declaration, false);
        campusService.updateCampusStatusRejectedDJ(declaration.getCampusId(), 3);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getVisitStatusAndDateByCampusId")
    public ResponseEntity<List<Map<String, Object>>> getVisitStatusAndDateByCampusId(@RequestParam("campusId") Long campusId) {
        List<SwornDeclaration> declarations = repository.findByCampusId(campusId);
        if (declarations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (SwornDeclaration declaration : declarations) {
            Map<String, Object> map = new HashMap<>();
            map.put("visitStatus", declaration.getVisitStatus());
            map.put("createdDate", declaration.getCreatedDate());
            responseList.add(map);
        }
        System.out.println(responseList.toString());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }



    private SwornDeclarationResponseDto mapSwornDeclarationToResponseDto(SwornDeclaration swornDeclaration) {
        SwornDeclarationResponseDto responseDto = new SwornDeclarationResponseDto();
        responseDto.setHasTrainedStaff(swornDeclaration.getHasTrainedStaff());
        responseDto.setHasAppropriateSignage(swornDeclaration.getHasAppropriateSignage());
        responseDto.setHasSuddenDeathProtocol(swornDeclaration.getHasSuddenDeathProtocol());
        responseDto.setHasMedicalEmergencySystem(swornDeclaration.getHasMedicalEmergencySystem());
        responseDto.setDeaCount(swornDeclaration.getDeaCount());
        responseDto.setId(swornDeclaration.getId());
        responseDto.setCampusId(swornDeclaration.getCampusId());

        Long campusId = swornDeclaration.getCampusId();
        String campusName = getCampusNameById(campusId);
        responseDto.setCampusName(campusName);

        return responseDto;
    }


    private String getCampusNameById(Long campusId) {
        Campus campus = campusRepository.findById(campusId).orElse(null);
        return campus != null ? campus.getName() : null;
    }
}