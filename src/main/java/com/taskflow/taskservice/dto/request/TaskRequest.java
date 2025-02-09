package com.taskflow.taskservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull String status
) {
}
