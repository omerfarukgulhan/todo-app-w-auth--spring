package com.todo.exception;

import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException() {
        super("NotUniqueEmailException");
    }

    public Map<String, String> getValidationErrors() {
        return Collections.singletonMap("email", "");
    }
}
