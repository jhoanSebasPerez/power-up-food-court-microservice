package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);

    Restaurant findById(Long idRestaurant);

    List<Restaurant> findAllRestaurant(Integer pageNumber, Integer pageSize);
}
