package com.taller.Proyecto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.dto.SwornDeclarationResponseDto;
import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.repository.SwornDeclarationRepository;

@Service
public class SwornDeclarationService {

    @Autowired
    private SwornDeclarationRepository repository;

    public SwornDeclaration saveDeclaration(SwornDeclaration declaration) {
    	declaration.setPending(true);
        return repository.save(declaration);
    }


    public List<SwornDeclarationResponseDto> getPendingDeclarations(List<String> selectedProvinces) {
        List<SwornDeclaration> pendingDeclarations = repository.findByPendingTrueAndCampusProvinceIn(selectedProvinces);

  
        List<SwornDeclarationResponseDto> responseDtos = new ArrayList<>();
        for (SwornDeclaration swornDeclaration : pendingDeclarations) {
            SwornDeclarationResponseDto responseDto = mapSwornDeclarationToResponseDto(swornDeclaration);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    public void updateDeclarationStatusToZero(Long declarationId) {
    	repository.updateDeclarationStatusToZero(declarationId);
    }
    private SwornDeclarationResponseDto mapSwornDeclarationToResponseDto(SwornDeclaration swornDeclaration) {
        SwornDeclarationResponseDto responseDto = new SwornDeclarationResponseDto();
        responseDto.setHasTrainedStaff(swornDeclaration.getHasTrainedStaff());
        responseDto.setHasAppropriateSignage(swornDeclaration.getHasAppropriateSignage());
        responseDto.setHasSuddenDeathProtocol(swornDeclaration.getHasSuddenDeathProtocol());
        responseDto.setHasMedicalEmergencySystem(swornDeclaration.getHasMedicalEmergencySystem());
        responseDto.setDeaCount(swornDeclaration.getDeaCount());
        responseDto.setCampusName(swornDeclaration.getCampus().getName()); 

        return responseDto;
    }
}
    
