package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.StatusDescription;

public interface StatusDescriptionRepository extends JpaRepository<StatusDescription, Long> {
    StatusDescription findByStatus(Integer status);
}