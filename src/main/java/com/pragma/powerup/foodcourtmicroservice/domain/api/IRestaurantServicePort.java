package com.pragma.powerup.foodcourtmicroservice.domain.api;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    Restaurant findById(Long idCategory);

    List<Restaurant> findAllRestaurant(Integer pageNumber, Integer pageSize);
}
