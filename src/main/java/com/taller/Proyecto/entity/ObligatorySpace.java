package com.taller.Proyecto.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taller.Proyecto.service.CardioAssisted;
import com.taller.Proyecto.service.InProcessToBeCardioAssisted;
import com.taller.Proyecto.service.StateSpace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
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
    
    /*
    @Column(name = "state_value", nullable = false)
    private String stateValue;
*/

    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean status;
 
	@Column(name = "cuit", nullable = false)
    private String CUIT;
    

    @ManyToMany
    @JoinTable(
        name = "campus_of_obligatory_spaces",
        joinColumns = @JoinColumn(name = "obligatory_space_id"),
        inverseJoinColumns = @JoinColumn(name = "campus_id")
    )
    @JsonIgnore
    private List<Campus> campuses;
    

}

