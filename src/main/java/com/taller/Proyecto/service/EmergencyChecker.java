package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taller.Proyecto.entity.Emergencies;
import com.taller.Proyecto.repository.EmergencyRepository;

import java.util.List;

@Component
public class EmergencyChecker {
	@Autowired
	private EmergencyNotifier emergencyNotifier;

    private final EmergencyRepository emergencyRepository;

    public EmergencyChecker(EmergencyRepository emergencyRepository) {
        this.emergencyRepository = emergencyRepository;
    }

    @Scheduled(cron = "0 */100 * * * ?") 
    public void checkEmergenciesStatus() {
        List<Emergencies> emergencies = emergencyRepository.findByStatusTrue();
        for (Emergencies emergency : emergencies) {
        	emergencyNotifier.notifyEmergency(emergency);
            System.out.println("Emergency detected at lat: " + emergency.getLatitude() + ", lon: " + emergency.getLongitude());
        }
    }
}
