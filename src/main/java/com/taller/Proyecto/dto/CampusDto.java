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
    
    private boolean isRepresented;
    
    @NotEmpty
    private String cuit;
    
    @NotEmpty
    private User representative;
  
    private Long representativeId;
    

    private Long obligatorySpaceId;
    
    private Integer status;
    
    private String campusState;


    
    

}
