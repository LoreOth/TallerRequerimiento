package com.taller.Proyecto.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.RepresentativeRequestDto;
import com.taller.Proyecto.entity.CampusRepresentative;
import com.taller.Proyecto.repository.CampusRepresentativeRepository;
import com.taller.Proyecto.service.CampusRepresentativeService;

@RestController
@RequestMapping("/campus/representatives")
public class CampusRepresentativeController {

    @Autowired
    private CampusRepresentativeService campusRepresentativeService;
    
    @Autowired
    private CampusRepresentativeRepository campusRepresentativeRepository;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public ResponseEntity<?> addRepresentative(@RequestBody RepresentativeRequestDto request) {
        try {
            CampusRepresentative representative = campusRepresentativeService.addRepresentative(request);
            return new ResponseEntity<>(representative, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkRepresentativeStatuses")
    public ResponseEntity<?> checkRepresentativeStatuses(@RequestParam Long userId) {
        try {
            List<CampusRepresentative> representativeStatuses = campusRepresentativeRepository.findByUserId(userId);
            if(representativeStatuses.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Map<Long, Integer> statusMap = representativeStatuses.stream()
            	    .collect(Collectors.toMap(
            	        representative -> representative.getCampus().getId(),
            	        CampusRepresentative::getStatus 
            	    ));

            return ResponseEntity.ok(statusMap);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }




}