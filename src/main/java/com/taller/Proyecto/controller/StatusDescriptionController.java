package com.taller.Proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.taller.Proyecto.service.StatusDescriptionService;

@RestController
@RequestMapping("/status")
public class StatusDescriptionController {

    @Autowired
    private StatusDescriptionService statusDescriptionService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-name")
    public ResponseEntity<String> getStatusName(@RequestParam Integer status) {
        String statusName = statusDescriptionService.getStatusName(status);
        return ResponseEntity.ok(statusName);
    }
}
