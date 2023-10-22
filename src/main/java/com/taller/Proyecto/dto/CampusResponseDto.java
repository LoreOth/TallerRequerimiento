package com.taller.Proyecto.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CampusResponseDto {
	  private Long id;
	    private String province;
	    private String name;
	    private String cuit;
	    private Integer status;
	    private String campusState;
}
