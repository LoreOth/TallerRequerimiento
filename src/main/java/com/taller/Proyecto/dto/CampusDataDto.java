package com.taller.Proyecto.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CampusDataDto {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String city;
    private String address;
    private String province;
    private String name;
    private Integer staff;
    private String cuit;
    private Integer visits;
    private String area;
    private Integer floors;
    private Integer permanentStaff;
    private Integer averageVisits;
}
