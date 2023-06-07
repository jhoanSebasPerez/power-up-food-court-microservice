
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 2;

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
        LocalDateTime fromDate = LocalDateTime.now().minusHours(3);

        return orderRepository.existsOrderInProcess(ordersIdByClient, states, fromDate);
    }

    @Override
    public List<Order> findAllByRestaurantAndState(Long restaurantId, String state, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        List<OrderEntity> orderEntities = orderRepository
                .findAllByRestaurantIdAAndState(restaurantId, state, pageRequest).getContent();

        return orderEntityMapper.toModelList(orderEntities);
    }

    @Override
    public boolean ordersBelongToRestaurant(List<Long> orders, Long restaurantId) {
        return orderRepository.ordersBelongToRestaurant(orders, restaurantId, orders.size());
    }

    @Override
    public void assignToOrderAndChangeToInPreparation(String chefDni, List<Long> orders) {
        orderRepository.assignToOrderAndChangeToInPreparation(chefDni, orders);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int requestPageNumber;
        int requestPageSize;

        if(pageNumber != null && pageNumber > 0)
            requestPageNumber = pageNumber;
        else
            requestPageNumber = DEFAULT_PAGE_NUMBER;

        if(pageSize != null && pageSize > 0)
            requestPageSize = pageSize;
        else
            requestPageSize = DEFAULT_PAGE_SIZE;

        Sort sort = Sort.by(Sort.Order.desc("date"));

        return PageRequest.of(requestPageNumber - 1, requestPageSize, sort);
    }
}
