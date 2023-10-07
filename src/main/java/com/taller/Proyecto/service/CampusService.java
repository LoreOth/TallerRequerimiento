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
        return campusRepository.findByObligatorySpaces_Id(obligatorySpaceId);
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
            
        // Añadir el espacio obligatorio al campus
        if (campus.getObligatorySpaces() == null) {
            campus.setObligatorySpaces(new ArrayList<>());
        }
        campus.getObligatorySpaces().add(obligatorySpace);

        // Aquí es donde manejas la otra parte de la relación:
        if (obligatorySpace.getCampuses() == null) {
            obligatorySpace.setCampuses(new ArrayList<>());
        }
        obligatorySpace.getCampuses().add(campus);

        campus.setProvince(obligatorySpace.getProvince());

        // Asociar el campus con su representante usando el id
        User representative = userRepository.findById(dto.getRepresentativeId())
                        .orElseThrow(() -> new EntityNotFoundException("User representative not found"));

        if (campus.getRepresentatives() == null) {
            campus.setRepresentatives(new ArrayList<>());
        }
        campus.getRepresentatives().add(representative);

        return campusRepository.save(campus);
    }
    public List<Campus> findCampusesByRepresentativeId(Long representativeId) {
        return campusRepository.findByRepresentatives_Id(representativeId);


}
}
