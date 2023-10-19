package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.SwornDeclaration;

public interface SwornDeclarationRepository extends JpaRepository<SwornDeclaration, Long> {
	
	
}