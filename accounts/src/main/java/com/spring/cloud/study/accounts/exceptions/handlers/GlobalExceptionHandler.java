package com.spring.cloud.study.accounts.exceptions.handlers;

import com.spring.cloud.study.accounts.exceptions.ResourceExistsException;
import com.spring.cloud.study.accounts.exceptions.ResourceNotFoundException;
import com.spring.cloud.study.accounts.models.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
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
    @ResponseBody
    public ErrorResponseDto handleGlobalException(Exception exception) {
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ResourceExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseDto handleResourceAlreadyExistsException(ResourceExistsException exception){
        return new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

}
