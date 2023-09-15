package com.taller.Proyecto.entity;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "obligatory_space")
public class ObligatorySpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String province;
    
    @ManyToOne
    @JoinColumn(name = "representative_id")
    private User representative;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String CUIT;
    
    @ManyToMany
    @JoinTable(
        name = "campus_of_obligatory_spaces",
        joinColumns = @JoinColumn(name = "obligatory_space_id"),
        inverseJoinColumns = @JoinColumn(name = "campus_id")
    )
    private List<Campus> campuses;
    
    // registros de la entidad Espacio Obligado pueden estar asociados a un único registro de la entidad User
    // nro cuit, razón social, nombre de sede

}
