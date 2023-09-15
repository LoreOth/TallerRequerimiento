package com.taller.Proyecto.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObligatoySpaceDto {
	private Long id;
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String province;
    
    @NotEmpty
    private UserDto representative;

    @NotEmpty
    private String CUIT;
    


	public UserDto getRepresentative() {
		return representative;
	}

	public void setRepresentative(UserDto representative) {
		this.representative = representative;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return this.name;
	}

	public void setname(String name) {
		this.name=name;
		
	}


}
