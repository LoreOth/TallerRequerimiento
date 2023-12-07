package com.taller.Proyecto.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.service.Etl;

import org.springframework.http.ResponseEntity;

@RestController
public class ETL {

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/execute-etl")
    public ResponseEntity<?> executeEtl() {
        try {
            Etl etl = new Etl();
            etl.executeEtlScript();
            return ResponseEntity.ok().body(new MessageResponse("ETL ejecutado con éxito"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new MessageResponse("Error al ejecutar ETL: " + e.getMessage()));
        }
    }

    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


