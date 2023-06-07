package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select case when count(o) > 0 then true else false end from OrderEntity o " +
            "where o.id IN(:orders) AND o.date >= (:date) AND o.state IN(:states)")
    boolean existsOrderInProcess(@Param("orders")List<Long> orders,
                                 @Param("states") List<String> states,
                                 @Param("date") LocalDateTime fromDate);

    @Query(value = "select case when count(o) = (:numberOrders)  then true else false end from OrderEntity o " +
            "where o.id IN(:orders) AND o.restaurant.id = (:restaurantId)")
    boolean ordersBelongToRestaurant(@Param("orders")List<Long> orders,
                                     @Param("restaurantId") Long restaurantId,
                                     @Param("numberOrders") Integer numberOrders);

    @Query(value = "select o from OrderEntity o " +
            "where o.id IN(:orders) AND o.date >= (:date) AND o.state IN(:states)")
    List<OrderEntity> findByStateAndDate(@Param("orders")List<Long> orders,
                                 @Param("states") List<String> states,
                                 @Param("date") LocalDateTime fromDate);

    @Query(value = "select o.id from OrderEntity o " +
            "where o.clientId = (:clientId)")
    List<Long> findAllByClientId(@Param("clientId") String clientId);

    @Query(value = "select o from OrderEntity o " +
            "where o.restaurant.id = (:restaurantId) AND o.state = (:state)")
    Page<OrderEntity> findAllByRestaurantIdAAndState(@Param("restaurantId") Long restaurantId,
                                            @Param("state") String state,
                                            Pageable pageable);

    @Transactional
    @Modifying
    @Query("update OrderEntity o set o.chefDni = (:chefDni), o.state = 'IN_PREPARATION' where o.id in (:orders)")
    void assignToOrderAndChangeToInPreparation(@Param("chefDni") String chefDni,
                                               @Param("orders") List<Long> orders);


}
