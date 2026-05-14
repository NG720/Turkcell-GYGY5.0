package com.library.exception;
import org.springframework.http.HttpStatus;
public class InsufficientCopiesException extends BusinessException {
    public InsufficientCopiesException(String kitap) {
        super("Mevcut kopya yok – kitap: " + kitap, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
