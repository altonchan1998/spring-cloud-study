package com.spring.cloud.study.accounts.exceptions;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String resourceName, String details){
        super(String.format("%s already exists with the given %s", resourceName, details));
    }
}
