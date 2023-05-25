package com.pragma.powerup.foodcourtmicroservice.domain.usecase;

import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.NotRestaurantOwnerException;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort,
                       ICategoryPersistencePort categoryPersistencePort,
                       IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveDish(String ownerDni, Dish dish) {
        if(!isOwnerRestaurant(ownerDni, dish.getRestaurant().getId()))
            throw new NotRestaurantOwnerException();

        if(!categoryPersistencePort.existById(dish.getCategory().getId()))
            throw new CategoryNotFoundException();

        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(String ownerDni, Dish dish) {
        if(!isOwnerRestaurant(ownerDni, dish.getRestaurant().getId()))
            throw new NotRestaurantOwnerException();
        dishPersistencePort.updateDish(dish);
    }


    private boolean isOwnerRestaurant(String ownerDni, Long idRestaurant) {
        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant);
        return restaurant.getOwnerDni().equals(ownerDni);
    }
}
