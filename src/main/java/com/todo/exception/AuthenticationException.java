package com.todo.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Invalid credentials");
    }

}