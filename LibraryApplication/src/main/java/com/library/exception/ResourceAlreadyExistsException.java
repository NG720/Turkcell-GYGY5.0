package com.library.exception;
import org.springframework.http.HttpStatus;
public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(String resource, String field, Object value) {
        super(resource + " zaten mevcut – " + field + ": " + value, HttpStatus.CONFLICT);
    }
}
