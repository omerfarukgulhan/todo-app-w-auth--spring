package com.todo.todo.dto;

import jakarta.validation.constraints.Size;

public record TodoUpdate(
        @Size(min = 3, max = 15)
        String title,
        @Size(min = 8, max = 30)
        String description) {
}
