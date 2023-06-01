package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishUpdateDto;

public interface IDishHandler {

    void saveDish(String token, DishRequestDto dishRequestDto);

    void updateDish(String token, Long dishId, DishUpdateDto dishUpdateDto);

    boolean enableDisable(String token, Long dishId, boolean enable);
}
