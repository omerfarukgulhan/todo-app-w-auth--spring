package com.todo.auth;

import com.todo.auth.dto.AuthResponse;
import com.todo.auth.dto.Credentials;
import com.todo.auth.token.Token;
import com.todo.auth.token.TokenService;
import com.todo.exception.AuthenticationException;
import com.todo.user.User;
import com.todo.user.UserService;
import com.todo.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Autowired
    public AuthService(TokenService tokenService, PasswordEncoder passwordEncoder, UserService userService) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userService.findByEmail(credentials.email());
        if (inDB == null) throw new AuthenticationException();
        if (!passwordEncoder.matches(credentials.password(), inDB.getPassword())) throw new AuthenticationException();
        Token token = tokenService.createToken(inDB, credentials);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(new UserDTO(inDB));
        return authResponse;

    }
}
