package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableMethodSecurity // allows @PreAuthorize later
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        var admin = User.withUsername("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .roles("ADMIN")
                        .build();

        var user = User.withUsername("user")
                       .password(passwordEncoder.encode("user123"))
                       .roles("USER")
                       .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // recommended
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // authorize requests
            .authorizeHttpRequests(auth -> auth
                // allow static resources, swagger, thymeleaf endpoints and login page to everyone
                .requestMatchers("/", "/css/**", "/js/**", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**", "/login", "/logout").permitAll()
                // allow Thymeleaf static pages, login, and the employee form only for authenticated users
                .requestMatchers("/employeeForm", "/saveEmployee", "/viewEmployees", "/viewEmp").hasAnyRole("ADMIN","USER")
                // file management and admin-only API (example)
                .requestMatchers("/files/**").hasRole("ADMIN")
                // REST api endpoints - restrict delete to ADMIN
                .requestMatchers("/api/employees/**").authenticated()
                // everything else requires authentication
                .anyRequest().authenticated()
            )
            // form login with default login page (we will make custom one later)
            .formLogin(form -> form
                .loginPage("/login")     // we will create custom page; comment this line to use default
                .defaultSuccessUrl("/main", true) // redirects here after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
            // for learning enable default CSRF (recommended for form submits)
            //.csrf(csrf -> csrf.disable());


        return http.build();
    }
}
