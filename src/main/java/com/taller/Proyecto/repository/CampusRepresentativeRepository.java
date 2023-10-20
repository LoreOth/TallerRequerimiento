package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taller.Proyecto.entity.CampusRepresentative;

@Repository
public interface CampusRepresentativeRepository extends JpaRepository<CampusRepresentative, Long> {


}
