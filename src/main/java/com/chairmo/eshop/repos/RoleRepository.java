package com.chairmo.eshop.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.Role;
import com.chairmo.eshop.domain.enums.ERole;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole roleName);
}
