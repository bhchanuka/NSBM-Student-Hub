package com.nsbm.studenthub.dto;

import lombok.*;

/**
 * DTO for Generic API Response Messages
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String message;
}
