package com.library.exception;
import org.springframework.http.HttpStatus;
public class AuthorizationException extends BusinessException {
    public AuthorizationException(String username, String requiredRoles) {
        super("Erişim reddedildi. Kullanıcı: '" + username + "' gerekli role sahip değil. Gerekli: " + requiredRoles, HttpStatus.FORBIDDEN);
    }
}
