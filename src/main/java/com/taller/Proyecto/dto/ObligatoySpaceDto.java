package com.taller.Proyecto.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
@ToString
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
    private Long representativeId;

    @NotEmpty
    private String CUIT;
    
    private boolean status;
}
