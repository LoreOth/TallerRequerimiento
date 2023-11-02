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

    public SwornDeclaration saveOrUpdateDeclaration(SwornDeclaration declaration, boolean status) {
        List<SwornDeclaration> existingDeclarations = repository.findByCampusId(declaration.getCampusId());
        declaration.setPending(status);
        if (!existingDeclarations.isEmpty()) {
            SwornDeclaration existingDeclaration = existingDeclarations.get(0);
            existingDeclaration.setHasAppropriateSignage(declaration.getHasAppropriateSignage());
            existingDeclaration.setHasMedicalEmergencySystem(declaration.getHasMedicalEmergencySystem());
            existingDeclaration.setHasSuddenDeathProtocol(declaration.getHasSuddenDeathProtocol());
            existingDeclaration.setHasTrainedStaff(declaration.getHasTrainedStaff());
            existingDeclaration.setObservations(declaration.getObservations());
            existingDeclaration.setPending(status);
            return repository.save(existingDeclaration);
        } else {
            return repository.save(declaration);
        }
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
    public SwornDeclaration updateDeclaration(SwornDeclaration declaration, Boolean status) {
        if (declaration.getId() == null) {
            throw new IllegalArgumentException("Declaration ID cannot be null for update");
        }
        declaration.setPending(status);
        return repository.save(declaration);
    }
    public SwornDeclaration updateVisitStatus(SwornDeclaration declaration, Boolean status) {
        if (declaration.getId() == null) {
            throw new IllegalArgumentException("Declaration ID cannot be null for update");
        }
        declaration.setVisitStatus(status);
        return repository.save(declaration);
    }
    
    
    public void setDeclarationStatusToZero(Long declarationId) {
    	repository.updateDeclarationStatusToZero(declarationId);
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
    
