package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select case when count(o) > 0 then true else false end from OrderEntity o " +
            "where o.id IN(:orders) AND o.date >= (:date) AND o.state IN(:states)")
    boolean existsOrderInProcess(@Param("orders")List<Long> orders,
                                 @Param("states") List<String> states,
                                 @Param("date") Date fromDate);

    @Query(value = "select o.id from OrderEntity o " +
            "where o.clientId = (:clientId)")
    List<Long> findAllByClientId(@Param("clientId") String clientId);
}
