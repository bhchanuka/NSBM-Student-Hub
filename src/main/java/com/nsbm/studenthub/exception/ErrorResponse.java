package com.nsbm.studenthub.exception;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response format
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> validationErrors;
}
