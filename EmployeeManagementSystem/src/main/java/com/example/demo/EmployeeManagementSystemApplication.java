package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication 
@ComponentScan(basePackages = {"com.example.demo","Controller","com.example.demo.exception"})
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "entity")
@EnableAutoConfiguration
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		
//		System.out.println("Encoded password for user123: " + 
//	            new BCryptPasswordEncoder().encode("user123"));
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("user123"));

	}

}