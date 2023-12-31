package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.CampusDataDto;
import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.dto.CampusWithRepresentativeDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.CampusRepresentative;
import com.taller.Proyecto.entity.DEA;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.entity.SwornDeclaration;
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
import java.util.Optional;

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

    public List<Campus> findAllCampuses() {
        return campusRepository.findAll();
    }

    public List<DEA> getDeasByCampus(Long campusId) {
        Campus campus = this.findCampusById(campusId);
        if (campus != null) {
            return campus.getDeas();
        } else {
            throw new EntityNotFoundException("Campus no encontrado con ID: " + campusId);
        }
    }
    
    @Transactional
    public Campus createCampus(CampusDto dto) {
        Campus campus = new Campus();
        campus.setCuit(dto.getCuit());
        campus.setName(dto.getName());
        campus.setStatus(1);
            
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

        User representative = userRepository.findById(dto.getRepresentativeId())
                        .orElseThrow(() -> new EntityNotFoundException("User representative not found"));

        if (campus.getCampusRepresentatives() == null) {
            campus.setCampusRepresentatives(new ArrayList<>());
        }

        CampusRepresentative campusRepresentative = new CampusRepresentative();
        campusRepresentative.setUser(representative);
        campusRepresentative.setCampus(campus);
        campusRepresentative.setStatus(1); 

        campus.getCampusRepresentatives().add(campusRepresentative);

        return campusRepository.save(campus);
    }
    
    @Transactional
    public void updateCampusStatusRejectedDJ(Long campusId, Integer newStatus) {
        Campus campus = campusRepository.findById(campusId)
            .orElseThrow(() -> new EntityNotFoundException("Campus not found"));

        campus.setStatus(newStatus);
        campusRepository.save(campus);
    }

    public List<Campus> findCampusesByRepresentativeId(Long representativeId) {
        return campusRepository.findByCampusRepresentatives_User_Id(representativeId);

    }
    public List<CampusWithRepresentativeDto> mapToDtoList(List<Campus> campuses) {
        List<CampusWithRepresentativeDto> dtoList = new ArrayList<>();
        for (Campus campus : campuses) {
            dtoList.add(mapToDto(campus));
        }
        return dtoList;
    }
    public Campus approveCampus(Long id) {
        Optional<Campus> optionalCampus = campusRepository.findById(id);
        if (optionalCampus.isPresent()) {
            Campus campus = optionalCampus.get();
            campus.setStatus(5);
            return campusRepository.save(campus);
        } else {
            throw new EntityNotFoundException("No se encontró el Campus con ID " + id);
        }
    }
    
    @Transactional
    public void updateObligatorySpacesStatus(Campus campus, boolean status) {
        List<ObligatorySpace> obligatorySpaces = campus.getObligatorySpaces();
        for (ObligatorySpace space : obligatorySpaces) {
            space.setStatus(status);
        }
    }
    public CampusWithRepresentativeDto mapToDto(Campus campus) {
        CampusWithRepresentativeDto dto = new CampusWithRepresentativeDto();
        dto.setCampusId(campus.getId());
        dto.setCampusName(campus.getName());
        dto.setCampusProvince(campus.getProvince());
        
        if(campus.getCampusRepresentatives() != null && !campus.getCampusRepresentatives().isEmpty()) {
            User representative = campus.getCampusRepresentatives().get(0).getUser();
            dto.setRepresentativeFirstName(representative.getFirstName());
            dto.setRepresentativeLastName(representative.getLastName());
        }
        
        return dto;
    }

    @Transactional
    public void updateCampusStatus(Long campusId, Integer status) {
        Campus campus = campusRepository.findById(campusId).orElse(null);
        if (campus != null) {
            campus.setStatus(status);
            campusRepository.save(campus);
        } else {
            throw new EntityNotFoundException("Campus no encontrado con ID: " + campusId);
        }
    }
    
    public List<Campus> findCampusesByProvincesAndStatus(List<String> provinces, Integer status) {
        return campusRepository.findByProvinceInAndStatus(provinces, status);
    }
    
    public int countDeasByCampusId(Long campusId) {
        Campus campus = campusRepository.findById(campusId).orElse(null);
        if (campus == null) {
            throw new RuntimeException("Campus no encontrado");
        }
        return campus.getDeas().size();
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
        
        campusRepository.save(campus);
    }
    
    public void addRepresentativeToCampus(Long campusId, Long userId) {

        Campus campus = campusRepository.findById(campusId).orElseThrow(() -> new RuntimeException("Campus no encontrado"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        CampusRepresentative campusRepresentative = new CampusRepresentative();
        campusRepresentative.setUser(user);
        campusRepresentative.setCampus(campus);
        campusRepresentative.setStatus(1);  

       
        if (campus.getCampusRepresentatives() == null) {
            campus.setCampusRepresentatives(new ArrayList<>());
        }
        campus.getCampusRepresentatives().add(campusRepresentative);

        campusRepository.save(campus);
    }
    
    public boolean shouldUpdateStatusToZero(SwornDeclaration declaration) {
        if (
            declaration.getHasTrainedStaff() &&
            declaration.getHasAppropriateSignage() &&
            declaration.getHasSuddenDeathProtocol() &&
            declaration.getHasMedicalEmergencySystem()
        ) {
            Campus campus = findCampusById(declaration.getCampusId());
            int deaCountInDb = countDeasByCampusId(declaration.getCampusId());
            return deaCountInDb == declaration.getDeaCount();
        }
        return false;
    }


}