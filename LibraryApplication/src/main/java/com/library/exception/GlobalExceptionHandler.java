package com.library.exception;

import com.library.dto.ErrorResponse;
import com.library.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Kimlik Doğrulama Hatası", "AUTHENTICATION_ERROR", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorization(AuthorizationException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Yetki Hatası", "AUTHORIZATION_ERROR", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Kayıt Bulunamadı", "RESOURCE_NOT_FOUND", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExists(ResourceAlreadyExistsException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Kayıt Zaten Mevcut", "RESOURCE_ALREADY_EXISTS", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(AlreadyReturnedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyReturned(AlreadyReturnedException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Zaten İade Edildi", "ALREADY_RETURNED", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InsufficientCopiesException.class)
    public ResponseEntity<ErrorResponse> handleInsufficient(InsufficientCopiesException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Yetersiz Kopya", "INSUFFICIENT_COPIES", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InactiveStudentException.class)
    public ResponseEntity<ErrorResponse> handleInactive(InactiveStudentException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "Pasif Öğrenci", "INACTIVE_STUDENT", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest req) {
        return build(ex.getHttpStatus(), "İş Kuralı Hatası", "BUSINESS_ERROR", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, List<String>> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                errors.computeIfAbsent(fe.getField(), k -> new ArrayList<>()).add(fe.getDefaultMessage()));

        return ResponseEntity.badRequest().body(ValidationErrorResponse.builder()
                .title("Doğrulama Hatası").type("VALIDATION_ERROR")
                .status(400).timestamp(LocalDateTime.now())
                .path(req.getRequestURI()).errors(errors).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Sunucu Hatası", "INTERNAL_ERROR",
                "Beklenmedik bir hata oluştu.", req.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String title, String type, String message, String path) {
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .title(title).type(type).message(message)
                .status(status.value()).timestamp(LocalDateTime.now()).path(path).build());
    }
}
