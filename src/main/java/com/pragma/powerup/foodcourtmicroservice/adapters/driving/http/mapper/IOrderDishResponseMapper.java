package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {

    OrderDishResponseDto toResponse(OrderDish orderDish);
}
