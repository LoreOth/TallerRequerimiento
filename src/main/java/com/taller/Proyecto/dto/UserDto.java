package com.taller.Proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
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
public class UserDto {

    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String lastName;

    @Null
    private String province;

    @Null
    private String address;
    
    @Null
    private String phone;
    
    private String rol;
    
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
