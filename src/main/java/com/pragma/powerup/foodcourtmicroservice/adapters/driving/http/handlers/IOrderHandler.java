package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;

public interface IOrderHandler{

    void saveOrder(String token, OrderRequestDto orderRequestDto);
}
