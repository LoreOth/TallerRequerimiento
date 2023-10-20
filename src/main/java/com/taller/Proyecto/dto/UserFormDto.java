package com.taller.Proyecto.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class UserFormDto {
    public String name;
    public String lastName;
    public String email;
    public String phone;
    public List<String> provinces;
    public String role;  
    public String password;
}
