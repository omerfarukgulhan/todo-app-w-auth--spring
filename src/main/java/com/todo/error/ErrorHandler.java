package com.todo.error;

import com.todo.exception.UnauthorizedException;
import com.todo.exception.NotFoundException;
import com.todo.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            NotFoundException.class,
            AuthenticationException.class,
            UnauthorizedException.class
    })
    public ApiResponse<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        if (exception instanceof NotFoundException) {
            apiError.setStatus(404);
        } else if (exception instanceof AuthenticationException) {
            apiError.setStatus(403);
            apiError.setMessage("deneme");
        } else if (exception instanceof UnauthorizedException) {
            apiError.setStatus(403);
            apiError.setMessage("UnauthorizedException deneme");
        }
        return new ApiResponse<>("fail", "Error occurred ", apiError);
    }
}

