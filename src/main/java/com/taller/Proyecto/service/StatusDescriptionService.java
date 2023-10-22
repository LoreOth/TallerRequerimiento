package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taller.Proyecto.entity.StatusDescription;
import com.taller.Proyecto.repository.StatusDescriptionRepository;

@Service
public class StatusDescriptionService {

    @Autowired
    private StatusDescriptionRepository statusDescriptionRepository;

    public String getStatusName(Integer status) {
        StatusDescription statusDescription = statusDescriptionRepository.findByStatus(status);
        if (statusDescription != null) {
            return statusDescription.getName();
        } else {
            return "Estado Desconocido";
        }
    }
}
