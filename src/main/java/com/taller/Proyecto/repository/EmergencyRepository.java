package com.taller.Proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.Emergencies;

public interface EmergencyRepository extends JpaRepository<Emergencies, Long> {
    List<Emergencies> findByStatusTrue();
    
}