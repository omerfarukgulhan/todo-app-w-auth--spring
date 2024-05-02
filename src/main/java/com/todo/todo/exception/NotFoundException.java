package com.todo.todo.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("There isn't any todo with id of " + id);
    }
}
