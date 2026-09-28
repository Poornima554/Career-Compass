package com.example.career_compass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.career_compass.security.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // JSP pages and static resources
                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/student",
                    "/admin",
                    "/error",
                    "/css/**",
                    "/js/**"
                ).permitAll()

                // Allow JSP internal forwards
                .dispatcherTypeMatchers(
                    DispatcherType.FORWARD,
                    DispatcherType.ERROR
                ).permitAll()

                // Swagger
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // Authentication APIs
                .requestMatchers(
                    "/api/auth/**"
                ).permitAll()

                // Public GET APIs
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/opportunities/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/skills/**"
                ).permitAll()

                // Admin APIs
                .requestMatchers(
                    "/api/admin/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/applications"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/applications/*/status"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/api/opportunities/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/api/skills/**"
                ).hasRole("ADMIN")

                // Student APIs
                .requestMatchers(
                    "/api/students/**",
                    "/api/applications/me",
                    "/api/recommendations/**"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/applications/*"
                ).hasRole("STUDENT")

                // Everything else
                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}