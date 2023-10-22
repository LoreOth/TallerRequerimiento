package com.taller.Proyecto.repository;

import com.taller.Proyecto.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {
	List<Campus> findByObligatorySpaces_Id(Long obligatorySpaceId);

	
	List<Campus> findByCampusRepresentatives_User_Id(Long representativeId);

	
	@Query("SELECT COUNT(c) FROM Campus c JOIN c.campusRepresentatives cr WHERE c.id = :campusId AND cr.user.id = :userId")
	Long countByCampusIdAndUserId(@Param("campusId") Long campusId, @Param("userId") Long userId);

	
	List<Campus> findByProvinceInAndStatus(List<String> provinces, Integer status);


}