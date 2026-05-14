package com.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter @Builder
public class ValidationErrorResponse {
    private final String title;
    private final String type;
    private final int status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;
    private final String path;
    private final Map<String, List<String>> errors;
}
