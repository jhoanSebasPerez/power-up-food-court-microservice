package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderState;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
                //.date(Date.from(LocalDateTime.now().minusHours(8).toInstant(ZoneOffset.of("-05:00"))))
                .date(LocalDateTime.now().minusHours(2))
                .build();

        orderRepository.save(order);

        OrderEntity order2 = OrderEntity.builder()
                .state(OrderState.PENDING.toString())
                .restaurant(RestaurantEntity.builder().id(2L).build())
                .dishes(new ArrayList<>())
                .date(LocalDateTime.now())
                .clientId(CLIENT_ID)
                .build();

        OrderEntity orderSaved2 = orderRepository.save(order2);

        orderSaved2.setDishes(List.of(
                OrderDishEntity.builder().dish(DishEntity.builder().id(3L).build()).order(OrderEntity.builder().id(orderSaved2.getId()).build()).quantity(2).build(),
                OrderDishEntity.builder().dish(DishEntity.builder().id(4L).build()).order(OrderEntity.builder().id(orderSaved2.getId()).build()).quantity(3).build()
        ));

        orderRepository.save(orderSaved2);
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
        //Date fromDate = Date.from(LocalDateTime.now().minusHours(3).toInstant(ZoneOffset.of("-05:00")));
        LocalDateTime fromDate = LocalDateTime.now().minusHours(3);

        List<OrderEntity> result = orderRepository.findByStateAndDate(ordersByClient, states, fromDate);

        assertNotNull(result);

    }

    @Test
    void findOrdersByRestaurantId() {
        Long restaurantId = 2L;
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<OrderEntity> orders = orderRepository.findAllByRestaurantIdAAndState(restaurantId,
                OrderState.PENDING.toString(), pageRequest);

        assertNotNull(orders);
    }


    @Test
    void    assignToOrderAndChangeToInPreparation() {
        String chefDni = "0101010";
        String state = OrderState.IN_PREPARATION.toString();
        List<Long> orders = List.of(1L, 2L);

        orderRepository.assignToOrderAndChangeToInPreparation(chefDni, orders);

        OrderEntity orderUpdated1 = orderRepository.findAll().get(0);
        OrderEntity orderUpdated2 = orderRepository.findAll().get(1);
        assertEquals(OrderState.IN_PREPARATION.toString(), orderUpdated1.getState());
    }

    @Test
    void ordersBelongToRestaurant() {
        Long restaurantId = 2L;
        List<Long> orders = List.of(1L, 3L);

        boolean result = orderRepository.ordersBelongToRestaurant(orders, restaurantId, orders.size());

        assertTrue(result);
    }
}