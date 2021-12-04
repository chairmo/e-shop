package com.chairmo.eshop.repos;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.chairmo.eshop.domain.User;
import org.springframework.data.domain.Pageable;


public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByLastName(String name, Pageable pageable);
	
	Optional<User> findByEmail(String email);
//	List<User> findByLastName(String lastName);
	Optional<User> findByPhone(String phone);

    Boolean existsByEmail(String email);

    @Query("SELECT u.emailVerified FROM User u WHERE u.email = ?1")
    Boolean findEmailVerifiedByEmail(String email);
}
