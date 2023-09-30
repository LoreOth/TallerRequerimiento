package com.taller.Proyecto.service;

import com.taller.Proyecto.dto.UserDto;
import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.repository.RoleRepository;
import com.taller.Proyecto.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto userDto) {
	    User user = new User();
	    user.setFirstName(userDto.getFirstName());
	    user.setEmail((String) userDto.getEmail());
	    user.setLastName(userDto.getLastName());
	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

	    if (userDto.getRol().equals("REPRE_EO")) {
	        Role adminRole = getOrCreateAdminRole();
	        user.setRoles(List.of(adminRole));
	    }

	    userRepository.save(user);
	}

	private Role getOrCreateAdminRole() {
	    // Try to find the ROLE_ADMIN in the database
	    Role role = roleRepository.findByName("ROLE_ADMIN");
	    
	    // If it's not found, create and save it
	    if (role == null) {
	        role = new Role();
	        role.setName("ROLE_ADMIN");
	        role = roleRepository.save(role);  // Save and re-assign to get the persisted entity with ID
	    }

	    return role;
	}

	@Override
	public User findUserByEmail(String email) {
	    System.out.println(
	    	    "Buscando usuario con email: " + email);
	   // Solo para depuración
	    return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> convertEntityToDto(user)).collect(Collectors.toList());
	}

	private UserDto convertEntityToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	@Override
	public void save(User existingUser) {
		userRepository.save(existingUser);
		
	}


}
