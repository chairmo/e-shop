package com.chairmo.eshop.rest.payload;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String email, firstName, lastName;
    private Collection<? extends GrantedAuthority>  roles;

    public AuthResponse(String accessToken, Long id, String email, String firstName,
                        String lastName, Collection<? extends GrantedAuthority>  roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    //    public AuthResponse(String accessToken) {
//        this.accessToken = accessToken;
//    }
}
