package com.todo.user.dto;

import jakarta.validation.constraints.Size;

public record UserUpdate(
        @Size(min = 4, max = 255)
        String username
) {
}
