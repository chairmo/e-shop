package com.chairmo.eshop.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    @NotNull
    private String appUrl;

    private final Auth auth = new Auth();

    @Data
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

    }

    public Auth getAuth() {
        return auth;
    }

}
