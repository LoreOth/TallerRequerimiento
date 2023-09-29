package com.taller.Proyecto.repository;



import com.taller.Proyecto.entity.ObligatorySpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObligatorySpaceRepository extends JpaRepository<ObligatorySpace, Long> {

	ObligatorySpace findByCUIT(String cuit);
}