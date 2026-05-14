package com.library.exception;
import org.springframework.http.HttpStatus;
public class InactiveStudentException extends BusinessException {
    public InactiveStudentException(String ogrenciNo) {
        super("Pasif öğrenci ödünç alamaz – öğrenciNo: " + ogrenciNo, HttpStatus.FORBIDDEN);
    }
}
