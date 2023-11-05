package com.spring.cloud.study.cards.exceptions.handlers;

import com.spring.cloud.study.cards.exceptions.ResourceExistsException;
import com.spring.cloud.study.cards.exceptions.ResourceNotFoundException;
import com.spring.cloud.study.cards.models.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return validationErrors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGlobalException(Exception exception) {
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ResourceExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDto handleResourceAlreadyExistsException(ResourceExistsException exception){
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }
}
