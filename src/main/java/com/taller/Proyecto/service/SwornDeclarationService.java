package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.entity.SwornDeclaration;
import com.taller.Proyecto.repository.SwornDeclarationRepository;

@Service
public class SwornDeclarationService {

    @Autowired
    private SwornDeclarationRepository repository;

    public SwornDeclaration saveDeclaration(SwornDeclaration declaration) {
        return repository.save(declaration);
    }

}