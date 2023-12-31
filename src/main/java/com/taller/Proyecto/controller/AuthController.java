package com.taller.Proyecto.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;

import com.taller.Proyecto.dto.LoginDto;
import com.taller.Proyecto.dto.UserDto;
import com.taller.Proyecto.dto.UserResponseDto;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.mappers.ApiResponse;
import com.taller.Proyecto.mappers.UserMapper;
import com.taller.Proyecto.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;


@RestController
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// http://localhost:8080/register/save
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/register/save")
	public ResponseEntity<Object> registration( @RequestBody UserDto userDto, BindingResult result) {
	    User existingUser = userService.findUserByEmail((String) userDto.getEmail());

	    if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
	        result.rejectValue("email", null, "There is already an account registered with the same email");
	        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Registration failed: There is already an account registered with the same email"));
	    }

	    if (result.hasErrors()) {
	        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Registration failed: Invalid user information"));
	    }

	    userService.saveUser(userDto);
	    return ResponseEntity.ok(Collections.singletonMap("message", "Registration successful"));
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/register/updateUser")
	public void updateUser(@RequestBody UserDto userDto) throws Exception {

	    User existingUser = userService.findUserByEmail(userDto.getEmail());
	    if (existingUser == null) {
	        throw new Exception("User with email " + userDto.getEmail() + " not found.");
	    }

	    existingUser.setFirstName(userDto.getFirstName());
	    existingUser.setLastName(userDto.getLastName());
	    existingUser.setPhone(userDto.getPhone());
	    existingUser.setAddress(userDto.getAddress());
	    existingUser.setProvince(userDto.getProvince());

	    userService.save(existingUser);
	}



	// http://localhost:8080/register/login
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/register/login")
	public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
	    ApiResponse<UserResponseDto> response = new ApiResponse<>();

	    if (result.hasErrors()) {
	        response.setError("Error de validación");
	        return ResponseEntity.badRequest().body(response);
	    }
	    
	    User user = userService.findUserByEmail(loginDto.getEmail());

	    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
	        response.setError("Credenciales inválidas");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    UserResponseDto userDto = UserMapper.toDTO(user);
	    response.setData(userDto);
	    return ResponseEntity.ok(response);
	}
	
	// http://localhost:8080/register/getCurrentUser
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/register/getCurrentUser")
	public ResponseEntity<UserResponseDto> getCurrentUser(@RequestParam String email) {
	    User user = userService.findUserByEmail(email);
	    System.out.println(user);
	    if (user != null) {
	        UserResponseDto userDto = UserMapper.toDTO(user);
	        return new ResponseEntity<>(userDto, HttpStatus.OK);
	    } else {
	        return ResponseEntity.notFound().build();  // 404 Not Found
	    }
	}

	

	@PostMapping("/register/logout")
	public ResponseEntity<String> logout() {
		// Perform logout logic here
		return ResponseEntity.ok("Cierre de sesión exitoso");
	}


	@GetMapping("register/users")
	public ResponseEntity<List<UserDto>> listAllUsers() {
		List<UserDto> users = userService.findAllUsers();
		return ResponseEntity.ok(users);
	}

}