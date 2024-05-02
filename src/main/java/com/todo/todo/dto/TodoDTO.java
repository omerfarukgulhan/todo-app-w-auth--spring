package com.todo.todo.dto;

import com.todo.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private int id;

    private String title;

    private String description;

    private boolean isCompleted;

    private LocalDateTime createdAt;

    public TodoDTO(Todo todo) {
        setId(todo.getId());
        setTitle(todo.getTitle());
        setDescription(todo.getDescription());
        setCompleted(todo.isCompleted());
        setCreatedAt(todo.getCreatedAt());
    }
}
