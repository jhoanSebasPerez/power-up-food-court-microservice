
package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IOrderDishRepository;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderState;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderDishRepository orderDishRepository;

    @Override
    public void saveOrder(Order order) {
        OrderEntity entity = OrderEntity.builder()
                .state(order.getState().toString())
                .restaurant(RestaurantEntity.builder().id(order.getRestaurant().getId()).build())
                .dishes(new ArrayList<>())
                .clientId(order.getClientId())
                .build();

        OrderEntity orderSaved = orderRepository.save(entity);

        order.setId(orderSaved.getId());
        order.setDate(orderSaved.getDate());

        order.setDishes(order.getDishes().stream().map(orderDish -> {
            orderDish.setOrder(new Order());
            orderDish.getOrder().setId(orderSaved.getId());
            return orderDish;
        }).toList());


        orderRepository.save(orderEntityMapper.toEntity(order));

    }

    @Override
    public boolean clientHasOrdersInProcess(String clientDni) {
        List<Long> ordersIdByClient = orderRepository.findAllByClientId(clientDni);

        List<String> states = Stream.of(OrderState.PENDING, OrderState.IN_PREPARATION, OrderState.COMPLETED)
                .map(Enum::toString).toList();

        //to filter by date three hours before
        Date fromDate = Date.from(LocalDateTime.now().minusHours(3).toInstant(ZoneOffset.of("-05:00")));

        return orderRepository.existsOrderInProcess(ordersIdByClient, states, fromDate);
    }
}
