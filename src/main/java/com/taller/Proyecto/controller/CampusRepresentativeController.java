package com.taller.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.RepresentativeRequestDto;
import com.taller.Proyecto.entity.CampusRepresentative;
import com.taller.Proyecto.service.CampusRepresentativeService;

@RestController
@RequestMapping("/campus/representatives")
public class CampusRepresentativeController {

    @Autowired
    private CampusRepresentativeService campusRepresentativeService;
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
}