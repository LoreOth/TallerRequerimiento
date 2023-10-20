package com.taller.Proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.UserFormDto;
import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.Staff;
import com.taller.Proyecto.repository.StaffRepository;
import com.taller.Proyecto.service.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffUserCreation {
    @Autowired
    private StaffService staffService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/userCreation")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserFormDto userForm) {
        try {
            Staff savedStaff = staffService.saveStaffWithRole(userForm);
            return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/managment/register/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserFormDto userForm) {
        Staff foundStaff = staffRepository.findByEmail(userForm.getEmail());
        if (foundStaff != null && passwordEncoder.matches(userForm.getPassword(), foundStaff.getPassword())) {
            Map<String, Object> data = new HashMap<>();
            data.put("email", foundStaff.getEmail());
            data.put("roles", foundStaff.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            data.put("id", foundStaff.getId());
            return ResponseEntity.ok(Map.of("data", data));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{staffId}/provinces")
    public ResponseEntity<List<String>> getProvincesForStaff(@PathVariable Long staffId) {
        try {
            Optional<Staff> staffOptional = staffRepository.findById(staffId);
            
            if (staffOptional.isPresent()) {
                Staff staff = staffOptional.get();
                return ResponseEntity.ok(staff.getProvinces());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("Staff not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(e.getMessage()));
        }
    }


}