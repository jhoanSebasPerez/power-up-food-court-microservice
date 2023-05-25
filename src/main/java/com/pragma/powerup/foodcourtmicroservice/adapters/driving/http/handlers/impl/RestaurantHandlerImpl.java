package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.client.IUserClient;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.exceptions.ClientErrorException;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IUserClient userClient;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Map<String, Boolean> body = userClient.isOwner(restaurantRequestDto.getOwnerDni()).getBody();
        if(body == null)
            throw new ClientErrorException("Type owner dni");
        boolean isOwner = body.get("response");
        if(!isOwner)
            throw new ClientErrorException("Not found owner with that id");
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public RestaurantResponseDto findById(Long idRestaurant) {
        return restaurantResponseMapper.toResponseDto(restaurantServicePort.findById(idRestaurant));
    }


}
