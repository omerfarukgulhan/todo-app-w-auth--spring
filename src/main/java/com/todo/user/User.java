package com.todo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.todo.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean active = false;

    @JsonIgnore
    private String activationToken;

    private String passwordResetToken;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Todo> todos;
}