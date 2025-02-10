package com.taskflow.taskservice.dto.response;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
