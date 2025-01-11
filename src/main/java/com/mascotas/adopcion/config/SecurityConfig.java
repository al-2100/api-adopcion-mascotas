package com.mascotas.adopcion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilita CSRF
                .csrf(csrf -> csrf.disable())
                // Configura el contexto de seguridad para requerir guardado explícito
                .securityContext(securityContext -> securityContext
                        .requireExplicitSave(true))
                // Configura la política de manejo de sesiones como STATELESS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura la autorización de las peticiones HTTP
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/usuarios").permitAll()
                        .requestMatchers("/api/usuarios/**").permitAll()
                        .requestMatchers("/api/mascotas").permitAll()
                        .requestMatchers("/api/mascotas/**").hasAnyRole("ADMIN", "ATENCION") // Permite a los roles ADMIN y ATENCION
                        .requestMatchers("/api/adopciones/**").hasAnyRole("ADMIN", "ATENCION") // Permite al rol ATENCION
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra petición
                )
                .httpBasic(basic -> {})
                .build();
    }

    // Configura el codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
