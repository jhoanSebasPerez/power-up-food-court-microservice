package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;

public interface IOrderPersistencePort {

    void saveOrder(Order order);

    boolean clientHasOrdersInProcess(String clientDni);
}
