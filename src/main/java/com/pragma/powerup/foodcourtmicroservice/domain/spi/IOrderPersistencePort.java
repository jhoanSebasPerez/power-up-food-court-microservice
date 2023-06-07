package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(Order order);

    boolean clientHasOrdersInProcess(String clientDni);

    List<Order> findAllByRestaurantAndState(Long restaurantId, String state, Integer pageNumber, Integer pageSize);

    boolean ordersBelongToRestaurant(List<Long> orders, Long restaurantId);

    void assignToOrderAndChangeToInPreparation(String chefDni, List<Long> orders);
}
