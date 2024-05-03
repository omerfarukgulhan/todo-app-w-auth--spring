package com.todo.user.dto;

import com.todo.user.User;
import lombok.Data;

@Data
public class UserDTO {
    private long id;

    private String username;

    private String email;

    public UserDTO(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
    }
}
