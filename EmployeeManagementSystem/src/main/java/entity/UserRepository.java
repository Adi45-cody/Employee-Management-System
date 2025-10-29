package entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.security.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
}//end of interface
