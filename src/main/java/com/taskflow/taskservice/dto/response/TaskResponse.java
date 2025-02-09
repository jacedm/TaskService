package com.taskflow.taskservice.dto.response;

public record TaskResponse(
        Long id, String title, String description, String status
) {
}
