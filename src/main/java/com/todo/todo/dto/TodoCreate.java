package com.todo.todo.dto;

import com.todo.todo.Todo;
import jakarta.validation.constraints.Size;

public record TodoCreate(
        @Size(min = 3, max = 15)
        String title,
        @Size(min = 8, max = 30)
        String description) {
    public Todo toTodo() {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        return todo;
    }
}
