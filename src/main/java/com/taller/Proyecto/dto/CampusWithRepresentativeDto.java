package com.taller.Proyecto.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CampusWithRepresentativeDto {
    private Long campusId;
    private String campusProvince;
    private String campusName;
    private String representativeFirstName;
    private String representativeLastName;

}
