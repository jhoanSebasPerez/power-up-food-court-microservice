package com.pragma.powerup.foodcourtmicroservice.domain.usecase;

import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             ICategoryPersistencePort categoryPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant findById(Long idRestaurant) {
        return restaurantPersistencePort.findById(idRestaurant);
    }

    @Override
    public List<Restaurant> findAllRestaurant(Integer pageNumber, Integer pageSize) {
        return restaurantPersistencePort.findAllRestaurant(pageNumber,pageSize);
    }

    @Override
    public List<Dish> findAllDishes(Long restaurantId, String categoryName, Integer pageNumber, Integer pageSize) {
        Restaurant restaurant = restaurantPersistencePort.findById(restaurantId);

        if(categoryName != null && !categoryName.isEmpty()) {
            Category category = categoryPersistencePort.findByName(categoryName);
            return restaurantPersistencePort.findAllDishesByCategory(restaurant, category, pageNumber, pageSize);
        }

        return restaurantPersistencePort.findAllDishes(restaurant, pageNumber, pageSize);
    }


}
