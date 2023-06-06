package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishRequestMapper {

    @Mapping(target = "dish.id", source = "dishId")
    OrderDish toModel(OrderDishRequestDto orderDishRequestDto);

    List<OrderDish> toModelList(List<OrderDishRequestDto> list);
}
