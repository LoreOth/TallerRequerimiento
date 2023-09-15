package com.taller.Proyecto.controller;

import jakarta.validation.Valid;

import com.taller.Proyecto.dto.LoginDto;
import com.taller.Proyecto.dto.UserDto;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

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

	@PostMapping("/register/save")
	public String registration(@Valid @RequestBody() UserDto userDto, BindingResult result, Model model) {
		User existingUser = userService.findUserByEmail((String) userDto.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/register";
		}

		userService.saveUser(userDto);
		return "redirect:/register?success";
	}

	// http://localhost:8080/register/login

	@PostMapping("/register/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
		if (result.hasErrors()) {
			// Manejar errores de validación
			return ResponseEntity.badRequest().body("Error de validación");
		}
		User user = userService.findUserByEmail(loginDto.getEmail());

		if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			// Las credenciales son inválidas
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}
		return ResponseEntity.ok("Inicio de sesión exitoso");
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