package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {


    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(target = "order", ignore = true)
    OrderDish toModel(OrderDishEntity entity);

    List<OrderDishEntity> toEntityList(List<OrderDish> list);
}
