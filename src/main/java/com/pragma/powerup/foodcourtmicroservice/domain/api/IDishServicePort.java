package com.pragma.powerup.foodcourtmicroservice.domain.api;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(String ownerDni, Dish dish);

    void updateDish(String ownerDni, Dish dish);

    boolean enableDisable(String ownerDni, Dish dish, boolean enable);

    Dish findById(Long id);
}
