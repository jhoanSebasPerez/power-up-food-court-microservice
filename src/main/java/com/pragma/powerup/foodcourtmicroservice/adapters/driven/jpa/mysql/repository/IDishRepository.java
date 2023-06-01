package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
}
