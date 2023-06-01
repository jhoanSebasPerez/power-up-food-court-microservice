package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions;

public class DishNotFound extends RuntimeException{

    public DishNotFound() {
    }

    public DishNotFound(String message) {
        super(message);
    }
}
