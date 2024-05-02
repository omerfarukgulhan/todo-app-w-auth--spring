package com.todo.error;

import com.todo.config.exception.ForbiddenException;
import com.todo.config.exception.UnauthorizedException;
import com.todo.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityErrorHandler {
    @ExceptionHandler({
            ForbiddenException.class,
            UnauthorizedException.class
    })
    public ApiResponse<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        if (exception instanceof ForbiddenException) {
            apiError.setStatus(HttpStatus.FORBIDDEN.value());
        } else if (exception instanceof UnauthorizedException) {
            apiError.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return new ApiResponse<>("fail", "Error occurred ", apiError);
    }
}