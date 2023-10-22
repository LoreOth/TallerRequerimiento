package com.taller.Proyecto.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class SwornDeclarationResponseDto {
    private Boolean hasTrainedStaff;
    private Boolean hasAppropriateSignage;
    private Boolean hasSuddenDeathProtocol;
    private Boolean hasMedicalEmergencySystem;
    private Integer deaCount;
    private String campusName;
    private Long id;
    private Long campusId;
	

}
