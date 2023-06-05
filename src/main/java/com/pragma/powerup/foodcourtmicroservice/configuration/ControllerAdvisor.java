package com.pragma.powerup.foodcourtmicroservice.configuration;


import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFound;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.exceptions.ClientErrorException;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.DishesNotBelongRestaurantException;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.NotRestaurantOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errorList = ex.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> field = new HashMap<>();
                    field.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return field;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, "Wrong credentials or role not allowed"));
    }

    @ExceptionHandler(DishesNotBelongRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(DishesNotBelongRestaurantException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, "All dishes must belong to the same restaurant"));
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientErrorException(ClientErrorException clientErrorException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, clientErrorException.getMessage()));
    }

    @ExceptionHandler(RestaurantNotFound.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFound(RestaurantNotFound restaurantNotFound) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, "Restaurant not found with that id"));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFound(CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, "Category not found with that id"));
    }

    @ExceptionHandler(NotRestaurantOwnerException.class)
    public ResponseEntity<Map<String, String>> handleNotRestaurantOwner(NotRestaurantOwnerException notRestaurantOwnerException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE_KEY, "The logged-in user is not the owner of the restaurant"));
    }
}
