package com.pragma.powerup.foodcourtmicroservice.domain.api;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;

public interface IOrderServicePort {

    void saveOrder(Order order);
}
