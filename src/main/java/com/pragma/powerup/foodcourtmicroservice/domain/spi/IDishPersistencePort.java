package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    void updateDish(Dish dish);

    boolean enableDisable(Dish dish, boolean enable);

    Dish findById(Long id);

    boolean existAllDishesByRestaurant(Long idRestaurant, List<Long> dishesIds);

    List<Dish> findAllDishes(Restaurant restaurant, Integer pageNumber, Integer pageSize);

    List<Dish> findAllDishesByCategory(Restaurant restaurant, Category category, Integer pageNumber, Integer pageSize);
}
