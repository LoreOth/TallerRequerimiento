package com.taller.Proyecto.entity;

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
@Table(name = "swornDeclaration")
public class SwornDeclaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean authorizedByHealthMinistry;
    private Boolean noDebts;
    private Boolean licensesUpToDate;
    private Boolean equalityPolicies;
    private Integer deaCount;
    private Long campusId;
    
}