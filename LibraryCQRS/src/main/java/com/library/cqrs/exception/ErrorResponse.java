package com.library.cqrs.exception;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
public class ErrorResponse {
    private String title;
    private String type;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}
