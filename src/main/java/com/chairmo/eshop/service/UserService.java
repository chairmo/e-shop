package com.chairmo.eshop.service;

import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.domain.VerificationToken;
import com.chairmo.eshop.model.UserDto;
import com.chairmo.eshop.rest.payload.PasswordRequest;
import com.chairmo.eshop.rest.payload.RequestPasswordReset;
import com.chairmo.eshop.rest.payload.SignUpRequest;
import com.chairmo.eshop.rest.payload.UpdateUser;
import java.util.List;

public interface UserService {

    User register(SignUpRequest request);

    User updateUser(UpdateUser user);

    User getUser();

    User saveUser(User user);

    User findByEmail(String email);

    boolean userExists(String email);

    boolean resetPassword(RequestPasswordReset passwordReset);

    boolean forgotPassword(PasswordRequest request);

    void verifyAccount(String token);

    Boolean getVerificationStatus();

    List<UserDto> getAllUsers(String name, int page, int size);

    VerificationToken createToken(User user);

    void deleteToken(VerificationToken token);

    VerificationToken findToken(String token);
}
