package com.taller.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.service.SwornDeclarationService;

@RestController
@RequestMapping("/documentation")
public class SwornDeclarationController {

    @Autowired
    private SwornDeclarationService service;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/SwornDeclarationSave")
    public SwornDeclaration saveDeclaration(@RequestBody SwornDeclaration declaration) {
        return service.saveDeclaration(declaration);
    }

}