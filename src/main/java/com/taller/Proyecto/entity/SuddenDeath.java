package com.taller.Proyecto.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "suddenDeath")
public class SuddenDeath {

	

    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 1000) 
    private String observations;
    
    @Column
    private String age;
    
    @Column
    private String name;
    
    @Column
    private Boolean rcp;
    
    @Column
    private Boolean dea;
    
    @Column
    private String date;
    
    @Column
    private Long campus_id;
    
}
