package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.projections.IRestaurantItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query("SELECT name AS name, urlLogo AS urlLogo FROM  RestaurantEntity restaurant")
    Page<IRestaurantItemView> findAllRestaurant(Pageable pageable);


}
