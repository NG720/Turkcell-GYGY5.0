package com.library.exception;
import org.springframework.http.HttpStatus;
public class AlreadyReturnedException extends BusinessException {
    public AlreadyReturnedException(Long oduncId) {
        super("Bu ödünç zaten iade edilmiş – id: " + oduncId, HttpStatus.CONFLICT);
    }
}
