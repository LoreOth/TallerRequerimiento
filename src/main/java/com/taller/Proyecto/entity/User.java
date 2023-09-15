package com.taller.Proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String encode) {
		this.password=encode;
		
	}

	public void setEmail(String email) {
		this.email=email;
		
	}

	public void setRoles(List<Role> of) {
		this.roles=of;
		
	}

	public void setName(String name) {
		this.name=name;
		
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public Collection<Role> getRoles() {
		return this.roles;
	}
	
	
	/*
	 * métodos `getRoles()` y `setRoles()` se generan automáticamente mediante la anotación `@Getter` y `@Setter` 
	 * de Lombok. Estos métodos te permiten acceder y asignar los roles del usuario.
	 * 
	 */
}