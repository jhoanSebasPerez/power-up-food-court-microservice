package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IDishRepositoryTest {

    @Autowired
    IDishRepository dishRepository;

    @Test
    void existDishesByRestaurant() {
        List<Long> dishesToFind = List.of(3L, 5L);
        Long restaurantId = 2L;

        List<Object> dishesReturned = dishRepository.dishesExistsByRestaurant(restaurantId, dishesToFind);

        assertEquals(dishesToFind.size(), dishesReturned.size());

    }
}