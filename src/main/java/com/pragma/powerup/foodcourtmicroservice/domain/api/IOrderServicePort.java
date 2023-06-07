package com.pragma.powerup.foodcourtmicroservice.domain.api;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;

import java.util.List;

public interface IOrderServicePort {

    void saveOrder(Order order);

    List<Order> findAllByRestaurantAndState(Long restaurantId, String state, Integer pageNumber, Integer pageSize);

    void assignToOrderAndChangeToInPreparation(String chefDni, Long restaurantId, List<Long> orders);
}
