package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.taller.Proyecto.entity.Emergencies;

@Component
public class EmergencyNotifier {

    private final SimpMessagingTemplate template;

    @Autowired
    public EmergencyNotifier(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyEmergency(Emergencies emergency) {
        String message = "Emergency detected at lat: " + emergency.getLatitude() + ", lon: " + emergency.getLongitude();
        template.convertAndSend("/topic/emergency", message);
    }
}
