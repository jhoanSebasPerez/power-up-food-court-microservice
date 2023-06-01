package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantItemDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {

    RestaurantResponseDto toResponseDto(Restaurant restaurant);

    List<RestaurantItemDto> toRestaurantResponseList(List<Restaurant> restaurantList);
}
