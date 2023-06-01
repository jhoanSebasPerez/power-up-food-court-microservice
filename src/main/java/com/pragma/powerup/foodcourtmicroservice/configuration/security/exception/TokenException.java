package com.pragma.powerup.foodcourtmicroservice.configuration.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException {

    public TokenException() {
        super("A problem with the token has occurred");
    }

    public TokenException(String explanation) {
        super(explanation);
    }
}
