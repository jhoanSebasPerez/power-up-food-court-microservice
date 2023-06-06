package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(uses = IOrderDishEntityMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toModel(OrderEntity orderEntity);

    List<Order> toModelList(List<OrderEntity> entityList);
}
