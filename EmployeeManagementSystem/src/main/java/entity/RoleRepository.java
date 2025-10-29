package entity;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
	
}//end of interface
