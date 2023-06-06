package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IOrderRepositoryTest {

    private static final String CLIENT_ID = "123456";

    @Autowired
    IOrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        OrderEntity order = OrderEntity.builder()
                .state(OrderState.PENDING.toString())
                .restaurant(RestaurantEntity.builder().id(2L).build())
                .dishes(new ArrayList<>())
                .clientId(CLIENT_ID)
                .date(Date.from(LocalDateTime.now().minusHours(8).toInstant(ZoneOffset.of("-05:00"))))
                .build();

        orderRepository.save(order);

        OrderEntity order2 = OrderEntity.builder()
                .state(OrderState.COMPLETED.toString())
                .restaurant(RestaurantEntity.builder().id(2L).build())
                .dishes(new ArrayList<>())
                .date(new Date())
                .clientId(CLIENT_ID)
                .build();

        orderRepository.save(order2);
    }

    @Test
    void createOrderAndThenCreateOrderDishesTest() {
        OrderEntity order = OrderEntity.builder()
                .state(OrderState.PENDING.toString())
                .restaurant(RestaurantEntity.builder().id(2L).build())
                .dishes(new ArrayList<>())
                .clientId(CLIENT_ID)
                .build();

        OrderEntity orderSaved = orderRepository.save(order);

        orderSaved.setDishes(List.of(
                OrderDishEntity.builder().dish(DishEntity.builder().id(3L).build()).order(OrderEntity.builder().id(orderSaved.getId()).build()).quantity(2).build(),
                OrderDishEntity.builder().dish(DishEntity.builder().id(4L).build()).order(OrderEntity.builder().id(orderSaved.getId()).build()).quantity(3).build()
        ));

        OrderEntity orderWithDishes = orderRepository.save(orderSaved);

        assertNotNull(orderWithDishes);
    }

    @Test
    void clientHasOrdersInProcessTest() {
        List<Long> ordersByClient = orderRepository.findAllByClientId(CLIENT_ID);
        List<String> states = Stream.of(OrderState.PENDING, OrderState.IN_PREPARATION, OrderState.COMPLETED)
                .map(Enum::toString).toList();
        Date fromDate = Date.from(LocalDateTime.now().minusHours(3).toInstant(ZoneOffset.of("-05:00")));

        boolean result = orderRepository.existsOrderInProcess(ordersByClient, states, fromDate);

        assertTrue(result);

    }
}