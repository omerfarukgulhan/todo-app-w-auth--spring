package com.todo.todo;

import com.todo.todo.dto.TodoCreate;
import com.todo.todo.dto.TodoDTO;
import com.todo.todo.dto.TodoUpdate;
import com.todo.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ApiResponse<Page<TodoDTO>> getTodos(Pageable page) {
        return new ApiResponse<>("success", "Data fetched successfully", todoService.getTodos(page).map(TodoDTO::new));
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoDTO> getTodo(@PathVariable int id) {
        return new ApiResponse<>("success", "Data fetched successfully", new TodoDTO(todoService.getTodo(id)));
    }

    @PostMapping
    public ApiResponse<TodoDTO> addTodo(@Valid @RequestBody TodoCreate todoCreate) {
        return new ApiResponse<>("success", "Data added successfully", new TodoDTO(todoService.addTodo(todoCreate.toTodo())));
    }

    @PatchMapping("/{id}")
    public ApiResponse<TodoDTO> updateTodo(@PathVariable int id, @Valid @RequestBody TodoUpdate todoUpdate) {
        return new ApiResponse<>("success", "Data updated successfully", new TodoDTO(todoService.updateTodo(id, todoUpdate)));
    }

    @PatchMapping("/{id}/toggle")
    public ApiResponse<TodoDTO> toggleTodo(@PathVariable int id) {
        return new ApiResponse<>("success", "Data updated successfully", new TodoDTO(todoService.toggleTodo(id)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
        return new ApiResponse<>("success", "Data deleted successfully", null);
    }
}
