package com.taller.Proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.DeaDto;
import com.taller.Proyecto.entity.DEA;
import com.taller.Proyecto.service.DEAService;

@RestController
public class DEAController {

    @Autowired
    private DEAService deaService;

 // http://localhost:8080/dea/saveDea
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/dea/saveDea")
    public void saveDea(@RequestBody DeaDto deaDto) {
        deaService.saveDeaWithCampus(deaDto.getBrand(), deaDto.getModel(), deaDto.getCampusId());
    }
    
 // http://localhost:8080/campus/{campusId}/deas
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/campus/{campusId}/deas")
    public List<DEA> getDeasByCampus(@PathVariable Long campusId) {
        return deaService.findDeasByCampus(campusId);
    }
}
	
	
	
	
	
	
	
	
	
	


