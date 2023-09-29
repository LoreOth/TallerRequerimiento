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
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = true)
    private String phone;
    
    @Column(nullable = true)
    private String address;
    
    public List<Campus> getRepresentedCampuses() {
		return representedCampuses;
	}

	public void setRepresentedCampuses(List<Campus> representedCampuses) {
		this.representedCampuses = representedCampuses;
	}

	@ManyToMany(mappedBy = "representatives")
    private List<Campus> representedCampuses; // Campus que este usuario representa

    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable = false, unique = true)
    private String email;

    public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable = false)
    private String password;
    
    @Column(nullable = true)
    private String province;


    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

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


	public String getEmail() {
		return this.email;
	}

	public Collection<Role> getRoles() {
		return this.roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	/*
	 * métodos `getRoles()` y `setRoles()` se generan automáticamente mediante la anotación `@Getter` y `@Setter` 
	 * de Lombok. Estos métodos te permiten acceder y asignar los roles del usuario.
	 * 
	 */
}