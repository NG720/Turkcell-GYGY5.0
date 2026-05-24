package com.library.cqrs.core.security.authorization;

public class AuthenticatedException extends RuntimeException {
    public AuthenticatedException(String message) {
        super(message);
    }
}
