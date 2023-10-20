package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
	Staff findByEmail(String email);
}