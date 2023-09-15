package com.taller.Proyecto.security;

import com.taller.Proyecto.entity.Role;
import com.taller.Proyecto.entity.User;
import com.taller.Proyecto.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    
    /*
     * El constructor `CustomUserDetailsService(UserRepository userRepository)` 
     * inyecta una instancia de `UserRepository` para poder acceder a los datos de 
     * los usuarios en la base de datos.
     * 
     */

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     *El método `loadUserByUsername(String email)` se ejecuta durante la autenticación cuando se proporciona 
     *un nombre de usuario (en este caso, el correo electrónico) para cargar los detalles del usuario correspondiente.
      El método busca un usuario en la base de datos utilizando el correo electrónico proporcionado.
     Si se encuentra un usuario, se crea una instancia de `org.springframework.security.core.userdetails.User` 
     que implementa la interfaz `UserDetails`. Se proporciona el correo electrónico y la contraseña del usuario, 
     así como los roles asignados al usuario
     * 
     */
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
    
    /*
     *  El método `mapRolesToAuthorities(Collection roles)` toma una colección de roles asignados a 
     *  un usuario y los mapea a una colección de `GrantedAuthority`, que representan los permisos 
     *  de un usuario en Spring Security
     * 
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}