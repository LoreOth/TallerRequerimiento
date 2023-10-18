package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.CampusDataDto;
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
            
       
        ObligatorySpace obligatorySpace = obligatorySpaceRepository.findById(dto.getObligatorySpaceId())
                        .orElseThrow(() -> new EntityNotFoundException("ObligatorySpace not found"));
            
     
        if (campus.getObligatorySpaces() == null) {
            campus.setObligatorySpaces(new ArrayList<>());
        }
        campus.getObligatorySpaces().add(obligatorySpace);

    
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
    public Campus findCampusById(Long id) {
        return campusRepository.findById(id).orElse(null);
    }
    public void updateCampus(CampusDataDto campusDataDto) {
   
        Campus campus = campusRepository.findById(campusDataDto.getId())
                .orElseThrow(() -> new RuntimeException("Campus no encontrado con ID: " + campusDataDto.getId()));

      
        campus.setName(campusDataDto.getName());
        campus.setLatitude(campusDataDto.getLatitude());
        campus.setLongitude(campusDataDto.getLongitude());
        campus.setProvince(campusDataDto.getProvince());
        campus.setCity(campusDataDto.getCity());
        campus.setAddress(campusDataDto.getAddress());
        campus.setArea(campusDataDto.getArea());
        campus.setFloors(campusDataDto.getFloors());
       // campus.setStaff(campusDataDto.getStaff());
       // campus.setVisits(campusDataDto.getVisits());

        // Guardamos el campus actualizado en la base de datos.
        campusRepository.save(campus);
    }
    
    public void addRepresentativeToCampus(Long campusId, Long userId) {
        // 1. Obtener las instancias de Campus y User usando los IDs.
        Campus campus = campusRepository.findById(campusId).orElseThrow(() -> new RuntimeException("Campus no encontrado"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Añadir el User a la lista de representatives del Campus.
        if (campus.getRepresentatives() == null) {
            campus.setRepresentatives(new ArrayList<>());
        }
        campus.getRepresentatives().add(user);

        // 3. Guardar la entidad Campus. 
        // Debido a la relación bidireccional, JPA actualizará automáticamente la tabla intermedia.
        campusRepository.save(campus);
    }
}