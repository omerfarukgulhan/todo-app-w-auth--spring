package com.todo.config.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You are not logged in");
    }
}
