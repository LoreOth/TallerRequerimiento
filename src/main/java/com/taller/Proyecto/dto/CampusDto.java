package com.taller.Proyecto.dto;

import com.taller.Proyecto.entity.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampusDto {
	
	private Long id;
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String province;
    
    @NotEmpty
    private User rerepresentative;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public User getRerepresentative() {
		return rerepresentative;
	}

	public void setRerepresentative(User rerepresentative) {
		this.rerepresentative = rerepresentative;
	}
    
    

}
