package com.todo.auth.token;

import com.todo.auth.dto.Credentials;
import com.todo.user.User;

public interface TokenService {
    Token createToken(User user, Credentials credentials);

    User verifyToken(String authorizationHeader);
}