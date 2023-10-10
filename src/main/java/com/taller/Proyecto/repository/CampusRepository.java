package com.taller.Proyecto.repository;

import com.taller.Proyecto.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {
	List<Campus> findByObligatorySpaces_Id(Long obligatorySpaceId);

	List<Campus> findByRepresentatives_Id(Long representativeId);

}