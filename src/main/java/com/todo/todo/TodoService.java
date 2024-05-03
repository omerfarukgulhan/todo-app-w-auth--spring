package com.todo.todo;

import com.todo.todo.dto.TodoUpdate;
import com.todo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Page<Todo> getTodos(Pageable page) {
        return todoRepository.findAll(page);
    }

    public Todo getTodo(int id) {
        return todoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(int id, TodoUpdate todoUpdate) {
        Todo todo = getTodo(id);

        if (todoUpdate.title() != null) todo.setTitle(todoUpdate.title());
        if (todoUpdate.description() != null) todo.setDescription(todoUpdate.description());

        return todoRepository.save(todo);
    }

    public Todo toggleTodo(int id) {
        Todo todo = getTodo(id);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(int id) {
        Todo todo = getTodo(id);
        todoRepository.delete(todo);
    }
}
