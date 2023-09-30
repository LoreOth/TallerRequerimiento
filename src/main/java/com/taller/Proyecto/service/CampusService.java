package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.ObligatorySpaceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CampusService {
    @Autowired
    private CampusRepository campusRepository;

    public List<Campus> findCampusesByObligatorySpaceId(Long obligatorySpaceId) {
        return campusRepository.findByObligatorySpaceId(obligatorySpaceId);
    }
    @Autowired
    private ObligatorySpaceRepository obligatorySpaceRepository;

    @Transactional
    public Campus createCampus(CampusDto dto) {
        Campus campus = new Campus();
        campus.setCuit(dto.getCuit());
        campus.setName(dto.getName());
        campus.setProvince("");
 

        // Asociar el campus con su ObligatorySpace usando el id
        ObligatorySpace obligatorySpace = obligatorySpaceRepository.findById(dto.getObligatorySpaceId()).orElseThrow(() -> new EntityNotFoundException("ObligatorySpace not found"));
        campus.setObligatorySpace(obligatorySpace);
        
        return campusRepository.save(campus);
    }
}
