package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.client.IUserClient;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantItemDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.exceptions.ClientErrorException;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IDishRequestMapper dishRequestMapper;
    private final IUserClient userClient;

    @Override
    public void saveRestaurant(String token, RestaurantRequestDto restaurantRequestDto) {
        Map<String, Boolean> body = userClient.isOwner(token, restaurantRequestDto.getOwnerDni());
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

    @Override
    public List<RestaurantItemDto> listRestaurant(Integer pageNumber, Integer pageSize) {
        List<Restaurant> restaurants = restaurantServicePort.findAllRestaurant(pageNumber, pageSize);
        return restaurantResponseMapper.toRestaurantResponseList(restaurants);
    }

    @Override
    public List<DishResponseDto> findAllDishes(Long restaurantId, String categoryName, Integer pageNumber, Integer pageSize) {
        List<Dish> dishes = restaurantServicePort.findAllDishes(restaurantId, categoryName, pageNumber, pageSize);
        return dishRequestMapper.toDishResponseList(dishes);
    }


}
