package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    void updateDish(Dish dish);

    boolean enableDisable(Dish dish, boolean enable);

    Dish findById(Long id);

    boolean existAllDishesByRestaurant(Long idRestaurant, List<Long> dishesIds);
}
