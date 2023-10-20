package com.taller.Proyecto.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.taller.Proyecto.service.StateSpace;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campus")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String province;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String cuit;
    
    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private String area;

    @Column
    private Integer floors;

    @Column
    private Integer permanentStaff;

    @Column
    private Integer averageVisits;
    
    @ManyToMany
    @JoinTable(
        name = "campus_dea", 
        joinColumns = @JoinColumn(name = "campus_id"),
        inverseJoinColumns = @JoinColumn(name = "dea_id")
    )
    private List<DEA> deas;
    
    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CampusRepresentative> campusRepresentatives;


    @ManyToMany(mappedBy = "campuses")
    private List<ObligatorySpace> obligatorySpaces;
    
    @Transient
    private StateSpace state;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean status;




    
}