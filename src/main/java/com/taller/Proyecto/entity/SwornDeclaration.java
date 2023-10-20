package com.taller.Proyecto.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "swornDeclaration")
public class SwornDeclaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean hasTrainedStaff;
    private Boolean hasAppropriateSignage;
    private Boolean hasSuddenDeathProtocol;
    private Boolean hasMedicalEmergencySystem;
    private Integer deaCount;
    private Long campusId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id")
    private List<MaintenanceStaff> maintenanceStaff = new ArrayList<>();

    
}