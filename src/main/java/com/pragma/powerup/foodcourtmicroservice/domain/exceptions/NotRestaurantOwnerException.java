package com.pragma.powerup.foodcourtmicroservice.domain.exceptions;

public class NotRestaurantOwnerException extends RuntimeException{

    public NotRestaurantOwnerException() {
    }

    public NotRestaurantOwnerException(String message) {
        super(message);
    }
}
