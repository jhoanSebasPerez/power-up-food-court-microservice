package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.composite_keys.OrderDishKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishKey> {
}
