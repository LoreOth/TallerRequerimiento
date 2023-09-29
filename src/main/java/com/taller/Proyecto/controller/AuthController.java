package com.taller.Proyecto.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;

import com.taller.Proyecto.dto.LoginDto;
import com.taller.Proyecto.dto.UserDto;
import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * 
	 * En el controlador `registration`, se recibe un objeto `UserDto` en el modelo
	 * que representa los datos del usuario a guardar. Se realiza una validación
	 * para verificar si ya existe un usuario registrado con el mismo correo
	 * electrónico.Se guarda el usuario . Y se crea un relacion en user roles de
	 * dicho usario y rol admin. utilizando el servicio `userService`
	 * 
	 */
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
	    // Primero, verifica si el usuario con el email dado existe
	    User existingUser = userService.findUserByEmail(userDto.getEmail());
	    if (existingUser == null) {
	        throw new Exception("User with email " + userDto.getEmail() + " not found.");
	    }

	    // Luego, actualiza cada campo del usuario con los valores del UserDto
	    existingUser.setFirstName(userDto.getFirstName());
	    existingUser.setLastName(userDto.getLastName());
	    existingUser.setPhone(userDto.getPhone());
	    existingUser.setAddress(userDto.getAddress());
	    existingUser.setProvince(userDto.getProvince());

	    // Guarda el usuario actualizado en la base de datos
	    userService.save(existingUser);
	}



	// http://localhost:8080/register/login
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/register/login")
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
	    if (result.hasErrors()) {
	        // Manejar errores de validación
	        Map<String, Object> response = new HashMap<>();
	        response.put("error", "Error de validación");
	        return ResponseEntity.badRequest().body(response);
	    }
	    User user = userService.findUserByEmail(loginDto.getEmail());

	    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
	        // Las credenciales son inválidas
	        Map<String, Object> response = new HashMap<>();
	        response.put("error", "Credenciales inválidas");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("email", user.getEmail());
	    response.put("id", user.getId());
	    List<Role> userRoles = (List<Role>) user.getRoles();
	    System.out.println(response);
	    response.put("role", userRoles.stream().map(Role::getName).collect(Collectors.toList()));

	    return ResponseEntity.ok(response);
	}
	
	// http://localhost:8080/register/getCurrentUser
	@CrossOrigin(origins = "http://localhost:3000")
	  @GetMapping("/register/getCurrentUser")
	    public ResponseEntity<User> getCurrentUser(@RequestParam String email) {
		User user = userService.findUserByEmail(email);
	        
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();  // 404 Not Found
	        }
	    }
	

	@PostMapping("/register/logout")
	public ResponseEntity<String> logout() {
		// Perform logout logic here
		return ResponseEntity.ok("Cierre de sesión exitoso");
	}

	// http://localhost:8080/register/users
	/*
	 * Lista los usuarios registrados en la base de datos
	 * 
	 */

	@GetMapping("register/users")
	public ResponseEntity<List<UserDto>> listAllUsers() {
		List<UserDto> users = userService.findAllUsers();
		return ResponseEntity.ok(users);
	}

}