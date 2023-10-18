package com.taller.Proyecto.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ObligatorySpaceResponseDto {
    private Long id;
    private String province;
    private String name;
    private boolean status;
    private String CUIT;
}
