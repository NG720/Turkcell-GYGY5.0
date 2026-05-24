package com.library.cqrs.exception;

import com.library.cqrs.core.security.authorization.AuthenticatedException;
import com.library.cqrs.core.security.authorization.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest req) {
        return ResponseEntity.status(ex.getStatus()).body(
            ErrorResponse.builder()
                .title(ex.getStatus().getReasonPhrase())
                .type(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .status(ex.getStatus().value())
                .timestamp(LocalDateTime.now())
                .path(req.getRequestURI())
                .build()
        );
    }

    // 401 — Token yok veya geçersiz
    @ExceptionHandler(AuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handle401(AuthenticatedException ex, HttpServletRequest req) {
        return ResponseEntity.status(401).body(
            ErrorResponse.builder()
                .title("Unauthorized")
                .type("AuthenticatedException")
                .message(ex.getMessage())
                .status(401)
                .timestamp(LocalDateTime.now())
                .path(req.getRequestURI())
                .build()
        );
    }

    // 403 — Token var ama yetki yok
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handle403(AuthorizationException ex, HttpServletRequest req) {
        return ResponseEntity.status(403).body(
            ErrorResponse.builder()
                .title("Forbidden")
                .type("AuthorizationException")
                .message(ex.getMessage())
                .status(403)
                .timestamp(LocalDateTime.now())
                .path(req.getRequestURI())
                .build()
        );
    }
}
