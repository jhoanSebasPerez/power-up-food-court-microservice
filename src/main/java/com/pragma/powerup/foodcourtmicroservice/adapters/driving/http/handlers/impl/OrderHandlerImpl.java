package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.client.IUserClient;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.exceptions.ClientErrorException;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.jwt.JwtUtil;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IUserClient userClient;
    private final IOrderResponseMapper orderResponseMapper;


    @Override
    public void saveOrder(String token, OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toModel(orderRequestDto);
        String clientDni = JwtUtil.getDniFromToken(token);
        order.setClientId(clientDni);
        orderServicePort.saveOrder(order);
    }

    @Override
    public List<OrderResponseDto> findAllByRestaurantAndState(String token, String state, Integer pageNumber, Integer pageSize) {
        String employeeDni = JwtUtil.getDniFromToken(token);

        Map<String, Long> result = userClient.findRestaurantIdByDni(token, employeeDni);
        if(result == null)
            throw new ClientErrorException("The employee is not assigned to a restaurant");

        Long restaurantId = result.get("restaurant");

        List<Order> orders = orderServicePort.findAllByRestaurantAndState(restaurantId, state, pageNumber, pageSize);

        return orderResponseMapper.toResponseList(orders);

    }
}
