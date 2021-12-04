package com.chairmo.eshop.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chairmo.eshop.config.AppProperties;
import com.chairmo.eshop.domain.Role;
import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.domain.VerificationToken;
import com.chairmo.eshop.domain.enums.ERole;
import com.chairmo.eshop.model.UserDto;
import com.chairmo.eshop.model.mapper.UserMapper;
import com.chairmo.eshop.repos.RoleRepository;
import com.chairmo.eshop.repos.TokenRepository;
import com.chairmo.eshop.repos.UserRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;
import com.chairmo.eshop.rest.payload.PasswordRequest;
import com.chairmo.eshop.rest.payload.RequestPasswordReset;
import com.chairmo.eshop.rest.payload.SignUpRequest;
import com.chairmo.eshop.rest.payload.UpdateUser;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;
	private final TokenRepository tokenRepository;
	private final AppProperties properties;
	
	public UserServiceImpl(final UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository,
			TokenRepository tokenRepository, AppProperties properties) {
		super();
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.roleRepository = roleRepository;
		this.tokenRepository = tokenRepository;
		this.properties = properties;
	}


	@Override
	public User register(SignUpRequest request) {
		if (userExists(request.getEmail())) {
			throw new InvalidExceptionError("User already exists.");
		}
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPassword(encoder.encode(request.getPassword()));

		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new InvalidExceptionError("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		return userRepository.save(user);
	}

	@Override
	public User getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOptional = userRepository.findByEmail(username);

		if (userOptional.isPresent()) {
			throw new InvalidExceptionError("User not found");
		}
		return userOptional.get();
	}

	@Override
	public User saveUser(User user) {
		if (Objects.isNull(user)) {
			throw new InvalidExceptionError("User is null");
		}
		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new InvalidExceptionError("Email not found"));
	}

	@Override
	public boolean userExists(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Boolean getVerificationStatus() {
		return new User().getEmailVerified() == true;
	}

	@Override
	public boolean resetPassword(RequestPasswordReset passwordReset) {
		boolean status = false;
		User user = getUser();
		if (!encoder.matches(passwordReset.getOldPassword(), passwordReset.getNewPasswordConfirm())) {
			throw new InvalidExceptionError("Password does not match.");
		}
		if (encoder.matches(passwordReset.getNewPassword(), user.getPassword())) {
			throw new InvalidExceptionError("Password is thesame as old one," +
		" please type a different password.");
		}
		if (encoder.matches(passwordReset.getNewPassword(), passwordReset.getNewPasswordConfirm())) {
			user.setPassword(encoder.encode(passwordReset.getNewPassword()));
			userRepository.save(user);
			status = true;
		}
		return status;
	}

	@Override
	public boolean forgotPassword(PasswordRequest request) {
		boolean status = false;

		if (!checkToken(request.getToken())) {
			throw new InvalidExceptionError("Token Invalid or Linked expired " 
		+ " Generate new link from"
					+ properties.getAppUrl() + "/forgotPassword");
		}

		if (userExists(request.getEmail())) {
			User user = getUser();
			if (!request.getNewPassword().matches(request.getConfirmPassword())) {
				throw new InvalidExceptionError("Password does not match.");
			}
			if (encoder.matches(request.getNewPassword(), user.getPassword())) {
				throw new InvalidExceptionError(
						"Password is thesame as old one," + " please type a different password.");
			}
			if (encoder.matches(request.getNewPassword(), request.getConfirmPassword())) {
				user.setPassword(encoder.encode(request.getNewPassword()));
				saveUser(user);
				status = true;
			}
		} else {
			throw new InvalidExceptionError("No user found with this email id");
		}
		return status;
	}

	@Override
	public VerificationToken createToken(User user) {
		VerificationToken token = new VerificationToken(user);
		return tokenRepository.save(token);
	}

	@Override
	public void deleteToken(VerificationToken token) {
		tokenRepository.delete(token);
	}

	@Override
	public VerificationToken findToken(String token) {
		return tokenRepository.findByConfirmationToken(token).get();
	}

	@Override
	public List<UserDto> getAllUsers(String name, int page, int size) {
            if (Objects.isNull(page) || Objects.isNull(size)) {
			throw new InvalidExceptionError("Page and size cannot be null.");
		}
		PageRequest request = PageRequest.of(page, size);
                
		return UserMapper.USER_MAPPER.toListDto(userRepository.findByLastName(name, request));
	}

	@Override
	public User updateUser(UpdateUser updateUser) {
		User user = getUser();
		user.setFirstName(updateUser.getFirstName());
		user.setLastName(updateUser.getLastName());
		user.setMiddleName(updateUser.getMiddleName());
		user.setPhone(updateUser.getPhone());
		return saveUser(user);
	}

	private boolean checkToken(String tokn) {
		boolean valid = true;
		VerificationToken token = findToken(tokn);

		if (token == null) {
			valid = false;
		}
		Calendar calendar = Calendar.getInstance();

		if (token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
			valid = false;
		}
		return valid;
	}
	
	@Override
	public void verifyAccount(String token) {
		if (!checkToken(token)) {
			throw new InvalidExceptionError("Token Invalid or Linked expired " +
		" Generate new link from"
					+ properties.getAppUrl() + "/activation");
		}
		User user = getUser();
		user.setEmailVerified(true);
		saveUser(user);
	}

}
