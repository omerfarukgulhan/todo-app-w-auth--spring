package com.todo;

import com.todo.todo.Todo;
import com.todo.todo.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public CommandLineRunner todoCreator(TodoRepository todoRepository) {
        return (args) -> {
            for (var i = 1; i <= 25; i++) {
                Todo todo = new Todo();

                todo.setTitle("todo title "+i);
                todo.setDescription("todo description "+i);
                todo.setCompleted(i%2==0);

                todoRepository.save(todo);
            }
        };

    }
}
