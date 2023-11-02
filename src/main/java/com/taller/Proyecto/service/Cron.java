package com.taller.Proyecto.service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.Province;
import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.ProvinceRepository;
import com.taller.Proyecto.repository.SwornDeclarationRepository;

@Component
@Transactional
public class Cron {

	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private SwornDeclarationRepository swornDeclarationRepository;
	
	@Autowired
	private CampusRepository campusRepository;

	@Scheduled(cron = "0 */1 * * * ?") // CADA 1 MINUTO CONTROLA VENCIMIENTOS DE DJ
	public void validateDates() {
	    List<SwornDeclaration> pendingDeclarations = swornDeclarationRepository.findNotPendingWithCampus();
	    
	    for (SwornDeclaration sd : pendingDeclarations) {
	        Campus campus = sd.getCampus();
	        String provinceName = campus.getProvince();
	        Province province = provinceRepository.findByName(provinceName); 
	        if (province.getCreatedDate().isBefore(sd.getCreatedDate())) {
	            swornDeclarationRepository.updateDeclarationStatusToOne(sd.getId());
	            swornDeclarationRepository.updateDeclarationVisitStatus(sd.getId());
	            campusRepository.updateStatusById(sd.getCampusId(), 4);
	        }
	    }
	}

}


