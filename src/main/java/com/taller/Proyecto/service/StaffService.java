package com.taller.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taller.Proyecto.dto.UserFormDto;
import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.Staff;
import com.taller.Proyecto.repository.RoleRepository;
import com.taller.Proyecto.repository.StaffRepository;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoleRepository roleRepository;  
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Staff saveStaffWithRole(UserFormDto userForm) {
        Role role = roleRepository.findByName(userForm.getRole());
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        Staff staff = new Staff();
        staff.setName(userForm.getName());
        staff.setLastName(userForm.getLastName());
        staff.setEmail(userForm.getEmail());
        staff.setProvinces(userForm.getProvinces());
        staff.setPhone(userForm.getPhone());
        staff.getRoles().add(role);
        staff.setPassword(passwordEncoder.encode(userForm.getEmail()));
        return staffRepository.save(staff);
    }
}
