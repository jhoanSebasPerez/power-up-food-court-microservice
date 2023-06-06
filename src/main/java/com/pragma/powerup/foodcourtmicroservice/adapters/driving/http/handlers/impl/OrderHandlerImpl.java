package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.jwt.JwtUtil;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;


    @Override
    public void saveOrder(String token, OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toModel(orderRequestDto);
        String clientDni = JwtUtil.getDniFromToken(token);
        order.setClientId(clientDni);
        orderServicePort.saveOrder(order);
    }
}
