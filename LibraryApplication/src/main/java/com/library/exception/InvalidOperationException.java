package com.library.exception;
import org.springframework.http.HttpStatus;
public class InvalidOperationException extends BusinessException {
    public InvalidOperationException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
