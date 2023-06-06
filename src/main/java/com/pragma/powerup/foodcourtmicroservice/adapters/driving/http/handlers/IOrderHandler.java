package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler{

    void saveOrder(String token, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> findAllByRestaurantAndState(String token, String state, Integer pageNumber, Integer pageSize);
}
