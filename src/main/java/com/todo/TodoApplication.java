package com.todo;

import com.todo.todo.Todo;
import com.todo.todo.TodoRepository;
import com.todo.user.User;
import com.todo.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public CommandLineRunner todoCreator(TodoRepository todoRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return (args) -> {
            for (var i = 1; i <= 25; i++) {
                Todo todo = new Todo();

                todo.setTitle("todo title "+i);
                todo.setDescription("todo description "+i);
                todo.setCompleted(i%2==0);

                todoRepository.save(todo);
            }
            for(var i = 1; i <= 25;i++){
                User user = new User();
                user.setUsername("user"+i);
                user.setEmail("user"+i+"@mail.com");
                user.setPassword(passwordEncoder.encode("P4ssword"));
                user.setActive(i%2==0);
                userRepository.save(user);
            }
        };

    }
}
