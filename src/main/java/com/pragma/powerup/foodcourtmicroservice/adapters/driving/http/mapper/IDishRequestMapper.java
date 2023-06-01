package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    Dish toDish(DishRequestDto dishRequestDto);
}
