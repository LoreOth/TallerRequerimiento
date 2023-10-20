package com.taller.Proyecto.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taller.Proyecto.service.StateSpace;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "last_name")
	    private String lastName;

	    @Column(name = "email", unique = true)
	    private String email;

	    @Column(name = "phone")
	    private String phone;

	    @Column(name = "password")
	    private String password;
	    
	    @ElementCollection
	    @CollectionTable(name = "staff_provinces", joinColumns = @JoinColumn(name = "staff_id"))
	    @Column(name = "province")
	    private List<String> provinces = new ArrayList<>();
	
	
	
    @JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "staff_roles",
            joinColumns = {@JoinColumn(name = "STAFF_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
  
    private List<Role> roles = new ArrayList<>();
}
