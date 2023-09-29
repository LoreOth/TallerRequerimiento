package com.taller.Proyecto.service;

import java.util.List;

import com.taller.Proyecto.dto.ObligatoySpaceDto;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.repository.ObligatorySpaceRepository;
import com.taller.Proyecto.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObligatorySpaceService {

    private final ObligatorySpaceRepository obligatorySpaceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ObligatorySpaceService(ObligatorySpaceRepository obligatorySpaceRepository, UserRepository userRepository) {
        this.obligatorySpaceRepository = obligatorySpaceRepository;
		this.userRepository = userRepository;
    }

    public List<ObligatorySpace> getAllObligatorySpaces() {
    	List<ObligatorySpace> lista = obligatorySpaceRepository.findAll();
    	System.out.println(lista);
        return lista;
    }


    public ObligatorySpace createNewObligatorySpace(ObligatoySpaceDto dto) {
        ObligatorySpace obligatorySpace = new ObligatorySpace();

        User representative = userRepository.findById(dto.getRepresentativeId()).orElse(null);
        if (representative == null) {
            // Manejar el error si no se encuentra el representante.
        }

        obligatorySpace.setName(dto.getName());
        obligatorySpace.setProvince(dto.getProvince());
        obligatorySpace.setCUIT(dto.getCUIT());
        obligatorySpace.setRepresentative(representative);

        return obligatorySpaceRepository.save(obligatorySpace);
    }
    
    public ObligatorySpace findByCUIT(String cuit) {
        return obligatorySpaceRepository.findByCUIT(cuit);
    }
}
