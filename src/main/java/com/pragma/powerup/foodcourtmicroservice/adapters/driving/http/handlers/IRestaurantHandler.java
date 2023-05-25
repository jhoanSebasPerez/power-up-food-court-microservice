package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    RestaurantResponseDto findById(Long idRestaurant);
}
