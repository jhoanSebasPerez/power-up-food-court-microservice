package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantItemDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(String token, RestaurantRequestDto restaurantRequestDto);

    RestaurantResponseDto findById(Long idRestaurant);

    List<RestaurantItemDto> listRestaurant(Integer pageNumber, Integer pageSize);

    List<DishResponseDto> findAllDishes(Long restaurantId, String categoryName, Integer pageNumber, Integer pageSize);
}
