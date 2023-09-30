package com.taller.Proyecto.dto;

import com.taller.Proyecto.entity.User;

import jakarta.validation.constraints.NotEmpty;
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
public class CampusDto {
	
	private Long id;
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String province;
    
    @NotEmpty
    private String cuit;
    
    @NotEmpty
    private User rerepresentative;
    
    private Long obligatorySpaceId;

    
    

}
