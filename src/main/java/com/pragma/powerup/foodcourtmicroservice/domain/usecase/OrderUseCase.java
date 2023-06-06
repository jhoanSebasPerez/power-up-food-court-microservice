package com.pragma.powerup.foodcourtmicroservice.domain.usecase;

import com.pragma.powerup.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.ClientHasOrderInprocessException;
import com.pragma.powerup.foodcourtmicroservice.domain.exceptions.DishesNotBelongRestaurantException;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Order;
import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderState;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IRestaurantPersistencePort restaurantPersistencePort,
                        IDishPersistencePort dishPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveOrder(Order order) {
        Long restaurantId = order.getRestaurant().getId();
        restaurantPersistencePort.findById(restaurantId);

        List<Long> dishedIds = order.getDishes().stream().map(orderDish -> orderDish.getDish().getId()).toList();
        boolean allExists = dishPersistencePort.existAllDishesByRestaurant(restaurantId, dishedIds);

        //Dishes are not at the same restaurant
        if(!allExists)
            throw new DishesNotBelongRestaurantException();

        if(orderPersistencePort.clientHasOrdersInProcess(order.getClientId()))
            throw new ClientHasOrderInprocessException();

        order.setState(OrderState.PENDING);

        orderPersistencePort.saveOrder(order);
    }
}
