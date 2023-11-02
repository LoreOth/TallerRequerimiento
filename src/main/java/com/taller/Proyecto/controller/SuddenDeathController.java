package com.taller.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.entity.SuddenDeath;
import com.taller.Proyecto.service.SuddenDeathService;

@RestController
@RequestMapping("/suddenDeath")
public class SuddenDeathController {

    @Autowired
    private SuddenDeathService service;
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST)
    public SuddenDeath saveSuddenDeath(@RequestBody SuddenDeath suddenDeath) {
        return service.saveSuddenDeath(suddenDeath);
    }
}