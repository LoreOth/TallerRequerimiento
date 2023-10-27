package com.taller.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.Proyecto.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByName(String name);
}
