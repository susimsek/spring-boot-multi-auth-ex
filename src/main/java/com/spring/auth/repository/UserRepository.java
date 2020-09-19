package com.spring.auth.repository;


import com.spring.auth.model.Role;
import com.spring.auth.model.User;
import com.spring.auth.model.enumerated.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}