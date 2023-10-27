package com.taller.Proyecto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/SwornDeclarationSave")
    public SwornDeclaration saveDeclaration(@RequestBody SwornDeclaration declaration) {
        return service.saveDeclaration(declaration);
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
        service.updateDeclaration(declaration);
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