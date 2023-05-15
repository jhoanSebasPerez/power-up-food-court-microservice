package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
