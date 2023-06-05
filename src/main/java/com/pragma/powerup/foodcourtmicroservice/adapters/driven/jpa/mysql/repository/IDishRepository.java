package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Page<DishEntity> findAllByRestaurantAndActiveTrue(RestaurantEntity restaurant, Pageable pageable);

    Page<DishEntity> findAllByRestaurantAndCategoryAndActiveTrue(RestaurantEntity restaurant,
                                                                 CategoryEntity category,
                                                                 Pageable pageable);

    @Query(value = "select d.id from DishEntity d where d.restaurant.id=(:restaurantId) AND d.id IN(:dishes)")
    List<Object> dishesExistsByRestaurant(@Param("restaurantId")Long restaurantId,
                                            @Param("dishes") List<Long> dishes);


}
