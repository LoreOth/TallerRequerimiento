package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.DEA;

public interface DEARepository extends JpaRepository<DEA, Long> {

	DEA save(DEA dea);
}