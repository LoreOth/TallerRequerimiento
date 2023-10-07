package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.ObligatorySpaceRepository;
import com.taller.Proyecto.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Campus createCampus(CampusDto dto) {
        Campus campus = new Campus();
        campus.setCuit(dto.getCuit());
        campus.setName(dto.getName());
        

        // Asociar el campus con su ObligatorySpace usando el id
        ObligatorySpace obligatorySpace = obligatorySpaceRepository.findById(dto.getObligatorySpaceId())
                        .orElseThrow(() -> new EntityNotFoundException("ObligatorySpace not found"));
        campus.setObligatorySpace(obligatorySpace);
        campus.setProvince(obligatorySpace.getProvince());

        // Asociar el campus con su representante usando el id
        User representative = userRepository.findById(dto.getRepresentativeId())
                        .orElseThrow(() -> new EntityNotFoundException("User representative not found"));

        // Añade el representante a la lista de representantes del campus
        List<User> representatives = new ArrayList<>();
        representatives.add(representative);
        campus.setRepresentatives(representatives);
        
        return campusRepository.save(campus);
    }
}
