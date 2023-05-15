package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;

    @PostMapping
    ResponseEntity<Map<String, String>> saveRestaurant(@Validated @RequestBody RestaurantRequestDto restaurantRequestDto){
        restaurantHandler.saveRestaurant(restaurantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "restaurant created successful"));
    }
}
