package com.taller.Proyecto.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.DEA;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.DEARepository;
@Service
public class DEAService {

    @Autowired
    private DEARepository deaRepository;
    
    @Autowired
    private CampusRepository campusRepository;
    

    public List<DEA> findDeasByCampus(Long campusId) {
        Campus campus = campusRepository.findById(campusId).orElse(null);
        if (campus != null) {
            return campus.getDeas();
        } else {
            throw new RuntimeException("Campus no encontrado con ID: " + campusId);
        }
    }

    public void saveDeaWithCampus(String brand, String model, Long campusId) {
        DEA dea = new DEA();
        dea.setBrand(brand);
        dea.setModel(model);
        dea.setDateMaintenance(new java.sql.Date(System.currentTimeMillis()));
        dea.setStatus(true);
        
        dea = deaRepository.save(dea);

        Campus campus = campusRepository.findById(campusId).orElseThrow(() -> new RuntimeException("Campus no encontrado"));

        campus.getDeas().add(dea);
        
        campusRepository.save(campus);
    }
    
    
}
	

