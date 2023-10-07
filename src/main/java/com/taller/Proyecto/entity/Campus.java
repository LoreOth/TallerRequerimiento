package com.taller.Proyecto.entity;

import java.util.List;

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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    
    @ManyToMany
    @JoinTable(
        name = "campus_representatives", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "campus_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> representatives;

    
    @ManyToOne
    @JoinColumn(name = "obligatory_space_id")
    private ObligatorySpace obligatorySpace;
    
    @ManyToMany(mappedBy = "campuses")
    private List<ObligatorySpace> obligatorySpaces;
    
    // Esto no se mapeará directamente a la base de datos, en cambio, usarás otro campo (como un Enum o String) para representar el estado en la BD
    @Transient
    private StateSpace state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public ObligatorySpace getObligatorySpace() {
		return obligatorySpace;
	}

	public void setObligatorySpace(ObligatorySpace obligatorySpace) {
		this.obligatorySpace = obligatorySpace;
	}

	public List<ObligatorySpace> getObligatorySpaces() {
		return obligatorySpaces;
	}

	public void setObligatorySpaces(List<ObligatorySpace> obligatorySpaces) {
		this.obligatorySpaces = obligatorySpaces;
	}


    
    //muchos Campus pueden tener una única ObligatorySpace
}
