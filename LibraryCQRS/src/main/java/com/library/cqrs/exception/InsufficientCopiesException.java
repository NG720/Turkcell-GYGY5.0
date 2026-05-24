package com.library.cqrs.exception;

import org.springframework.http.HttpStatus;

public class InsufficientCopiesException extends BusinessException {
    public InsufficientCopiesException(String kitapAdi) {
        super("'" + kitapAdi + "' kitabının mevcut kopyası yok.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
