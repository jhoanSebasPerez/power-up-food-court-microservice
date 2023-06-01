package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions;

public class RestaurantNotFound extends RuntimeException{

    public RestaurantNotFound() {
        super();
    }

    public RestaurantNotFound(String message) {
        super(message);
    }
}
