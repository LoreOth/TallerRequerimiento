package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.entity.SuddenDeath;
import com.taller.Proyecto.repository.SuddenDeathRepository;

@Service
public class SuddenDeathService {

    @Autowired
    private SuddenDeathRepository repository;

    public SuddenDeath saveSuddenDeath(SuddenDeath suddenDeath) {
        return repository.save(suddenDeath);
    }
}