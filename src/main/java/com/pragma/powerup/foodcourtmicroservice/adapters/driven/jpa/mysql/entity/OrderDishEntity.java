package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.composite_keys.OrderDishKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDishEntity {

    @EmbeddedId
    private OrderDishKey key = new OrderDishKey();

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private DishEntity dish;
}
