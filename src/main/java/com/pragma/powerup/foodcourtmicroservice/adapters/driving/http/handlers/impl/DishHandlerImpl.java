package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.jwt.JwtUtil;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;


    @Override
    public void saveDish(String token, DishRequestDto dishRequestDto) {
        String ownerDni = JwtUtil.getOwnerDni(token);
        dishServicePort.saveDish(ownerDni, dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(String token, Long dishId, DishUpdateDto dishUpdateDto) {
        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setPrice(dishUpdateDto.getPrice());
        dish.setDescription(dishUpdateDto.getDescription());

        Restaurant restaurant = new Restaurant();
        restaurant.setId(dishUpdateDto.getRestaurantId());
        dish.setRestaurant(restaurant);

        String ownerDni = JwtUtil.getOwnerDni(token);

        dishServicePort.updateDish(ownerDni, dish);
    }

    @Override
    public boolean enableDisable(String token, Long dishId, boolean enable) {
        Dish dish = dishServicePort.findById(dishId);

        String ownerDni = JwtUtil.getOwnerDni(token);

        return dishServicePort.enableDisable(ownerDni, dish, enable);
    }
}
