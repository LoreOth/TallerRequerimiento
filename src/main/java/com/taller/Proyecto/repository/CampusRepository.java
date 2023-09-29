package com.taller.Proyecto.repository;

import com.taller.Proyecto.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    List<Campus> findByObligatorySpaceId(Long obligatorySpaceId);
}