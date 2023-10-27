package com.taller.Proyecto.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.Province;
import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.repository.ProvinceRepository;
import com.taller.Proyecto.repository.SwornDeclarationRepository;

@Component
public class Cron {

	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private SwornDeclarationRepository swornDeclarationRepository;

	@Scheduled(cron = "0 */01 * * * ?")
	public void validateDates() {
	    List<SwornDeclaration> pendingDeclarations = swornDeclarationRepository.findNotPendingWithCampus();
	    
	    for (SwornDeclaration sd : pendingDeclarations) {
	        Campus campus = sd.getCampus();
	        String provinceName = campus.getProvince();
	        Province province = provinceRepository.findByName(provinceName); 
	        
	        if (province.getCreatedDate().isBefore(sd.getCreatedDate())) {
	            swornDeclarationRepository.updateDeclarationStatusToOne(sd.getId());
	        }
	    }
	}

}


