package com.todo.auth.dto;

import com.todo.auth.token.Token;
import com.todo.user.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    private UserDTO user;

    private Token token;
}
