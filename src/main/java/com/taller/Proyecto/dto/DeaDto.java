package com.taller.Proyecto.dto;

import java.sql.Date;

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
public class DeaDto {
	private Long id;
    @NotEmpty
    private String brand;
    
    @NotEmpty
    private String model;
    
    @NotEmpty
    private Long campusId;
    
    private Date dateMaintenance;
    
    private Boolean status;
    
}
