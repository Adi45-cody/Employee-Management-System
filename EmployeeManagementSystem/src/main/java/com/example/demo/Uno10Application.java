package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication 
@ComponentScan(basePackages = {"com.example.demo","Controller","com.example.demo.exception"})
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "entity")
@EnableAutoConfiguration
public class Uno10Application {

	public static void main(String[] args) {
		SpringApplication.run(Uno10Application.class, args);
	}

}