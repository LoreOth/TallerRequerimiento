package com.taller.Proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.taller.Proyecto.entity.SwornDeclaration;

import jakarta.transaction.Transactional;

public interface SwornDeclarationRepository extends JpaRepository<SwornDeclaration, Long> {
	
	List<SwornDeclaration> findByPendingTrue();
	
	List<SwornDeclaration> findByPendingTrueAndCampusProvinceIn(List<String> selectedProvinces);
	
    @Transactional
    @Modifying
    @Query("UPDATE SwornDeclaration sd SET sd.pending = false WHERE sd.id = :declarationId")
    void updateDeclarationStatusToZero(Long declarationId);
}