package com.taller.Proyecto.repository;

import com.taller.Proyecto.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {
	List<Campus> findByObligatorySpaces_Id(Long obligatorySpaceId);

	List<Campus> findByRepresentatives_Id(Long representativeId);
	
	@Query("SELECT COUNT(c) FROM Campus c JOIN c.representatives r WHERE c.id = :campusId AND r.id = :userId")
    Long countByCampusIdAndUserId(@Param("campusId") Long campusId, @Param("userId") Long userId);


}