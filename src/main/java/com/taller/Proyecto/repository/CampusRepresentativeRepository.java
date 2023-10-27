package com.taller.Proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taller.Proyecto.entity.CampusRepresentative;

@Repository
public interface CampusRepresentativeRepository extends JpaRepository<CampusRepresentative, Long> {

	
	List<CampusRepresentative> findByUserId(Long userId);
}
