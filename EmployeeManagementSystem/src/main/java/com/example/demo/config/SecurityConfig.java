package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    //  In-memory users for testing
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

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ✅ disable CSRF for Postman + Swagger testing

            .authorizeHttpRequests(auth -> auth
                // allow Swagger, login, static pages without login
                .requestMatchers("/", "/css/**", "/js/**", "/webjars/**",
                                 "/swagger-ui/**", "/v3/api-docs/**", "/login", "/logout").permitAll()
                // allow Thymeleaf views for authenticated users
                .requestMatchers("/employeeForm", "/saveEmployee", "/viewEmployees", "/viewEmp", "/main")
                    .hasAnyRole("ADMIN", "USER")
                // secure APIs - login required
                .requestMatchers("/api/**").authenticated()
                // everything else
                .anyRequest().authenticated()
            )

            //  for Browser login form
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/main", true)
                .permitAll()
            )

            // for Postman + Swagger (Basic Auth)
            .httpBasic(Customizer.withDefaults())

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
