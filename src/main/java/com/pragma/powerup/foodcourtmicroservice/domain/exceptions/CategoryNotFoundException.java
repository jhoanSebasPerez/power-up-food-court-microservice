package com.pragma.powerup.foodcourtmicroservice.domain.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
