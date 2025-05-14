package com.proyecto.Perfulandia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Para habilitar @PreAuthorize si la usas
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/productos").hasAnyRole("GERENTE", "VENTAS", "LOGISTICA")
                .requestMatchers(HttpMethod.POST, "/api/productos").hasRole("GERENTE")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("GERENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("GERENTE")
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build(),
            User.withUsername("gerente").password("{noop}gerente123").roles("GERENTE").build(),
            User.withUsername("ventas").password("{noop}ventas123").roles("VENTAS").build(),
            User.withUsername("logistica").password("{noop}logistica123").roles("LOGISTICA").build()
        );
    }

}
