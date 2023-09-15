package com.taller.Proyecto.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "campus")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String province;
    
    @ManyToOne
    @JoinColumn(name = "representative_id")
    private User representative;

    
    @ManyToOne
    @JoinColumn(name = "obligatory_space_id")
    private ObligatorySpace obligatorySpace;
    
    @ManyToMany(mappedBy = "campuses")
    private List<ObligatorySpace> obligatorySpaces;
    
    //muchos Campus pueden tener una única ObligatorySpace
}
