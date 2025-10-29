package com.example.demo.service;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import entity.UserRepository;
import entity.security.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

	    return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getUsername())
	            .password(user.getPassword())
	            .authorities(user.getRoles().stream()
	                    .map(role -> "ROLE_" + role.getName())
	                    .collect(Collectors.toList())
	                    .toArray(new String[0]))
	            .disabled(!user.isEnabled())
	            .build();
	}


	
	
}//end of class
