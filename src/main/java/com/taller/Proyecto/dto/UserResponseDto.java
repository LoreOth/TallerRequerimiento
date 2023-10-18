package com.taller.Proyecto.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String province;
    private String address;
    private String phone;
    private String email;
    private List<String> roles;
}
