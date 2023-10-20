package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.dto.CampusDto;
import com.taller.Proyecto.dto.RepresentativeRequestDto;
import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.CampusRepresentative;
import com.taller.Proyecto.repository.CampusRepository;
import com.taller.Proyecto.repository.CampusRepresentativeRepository;
import com.taller.Proyecto.repository.UserRepository;

import com.taller.Proyecto.entity.User;
@Service
public class CampusRepresentativeService {

    @Autowired
    private CampusRepresentativeRepository campusRepresentativeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CampusRepository campusRepository;

    public CampusRepresentative addRepresentative(RepresentativeRequestDto request) {
        CampusRepresentative representative = new CampusRepresentative();

        Campus campus = campusRepository.findById(request.getCampusId())
                .orElseThrow(() -> new RuntimeException("Campus not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        representative.setCampus(campus);
        representative.setUser(user);
        representative.setStatus(false);

        return campusRepresentativeRepository.save(representative);
    }
}
