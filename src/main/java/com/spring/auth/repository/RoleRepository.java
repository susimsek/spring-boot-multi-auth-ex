package com.spring.auth.repository;


import com.spring.auth.model.Role;
import com.spring.auth.model.enumerated.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}