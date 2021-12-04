package com.chairmo.eshop.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.repos.UserRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new InvalidExceptionError("User id not found")
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public boolean isAccountVerified(String email) {
        boolean isVerified = userRepository.findEmailVerifiedByEmail(email);
        return isVerified;
    }
}
