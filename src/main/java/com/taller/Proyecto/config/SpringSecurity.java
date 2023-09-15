package com.taller.Proyecto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // indica que esta clase es una configuración de Spring
@EnableWebSecurity // habilita la seguridad web en la aplicación.
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService; // El `UserDetailsService` se utiliza para cargar los detalles del usuario durante la autenticación.

    /*
     *  El método `passwordEncoder()` es un bean que devuelve un `PasswordEncoder`. 
     *  En este caso, se utiliza `BCryptPasswordEncoder` para codificar las contraseñas de los usuarios.
     */
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    /*
     * 
     * El método `filterChain(HttpSecurity http)` configura las reglas de seguridad para las diferentes 
     * rutas de la aplicación. Aquí se deshabilita el token CSRF, se configuran las reglas de autorización 
     * para diferentes rutas y se configura el formulario de inicio de sesión y cierre de sesión
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    /*
     * El método `configureGlobal(AuthenticationManagerBuilder auth)` configura el `AuthenticationManagerBuilder` 
     * para utilizar el `UserDetailsService` y el `PasswordEncoder` para autenticar a los usuarios.
     */
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}