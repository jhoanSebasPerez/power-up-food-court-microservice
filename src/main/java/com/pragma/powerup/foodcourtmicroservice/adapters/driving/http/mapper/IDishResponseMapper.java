package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {

    DishResponseDto toResponseDto(Dish dish);
}
