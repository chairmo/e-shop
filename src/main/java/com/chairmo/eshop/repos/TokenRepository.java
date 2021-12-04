package com.chairmo.eshop.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
Optional<VerificationToken> findByConfirmationToken(String token);
}
