package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.UserDto;
import com.taller.Proyecto.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

	User findUserByEmail(String email);
}