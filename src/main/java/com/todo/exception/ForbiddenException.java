package com.todo.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You are not authorized for that action");
    }
}