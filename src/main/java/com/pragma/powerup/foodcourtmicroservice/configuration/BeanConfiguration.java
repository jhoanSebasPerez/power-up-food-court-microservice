package com.pragma.powerup.foodcourtmicroservice.configuration;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.OrderMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.*;
import com.pragma.powerup.foodcourtmicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderDishRepository orderDishRepository;

    @Bean
    IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantMysqlAdapter(restaurantRepository,
                dishRepository,
                restaurantEntityMapper,
                dishEntityMapper,
                categoryEntityMapper);
    }

    @Bean
    IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(), categoryPersistencePort());
    }


    @Bean
    IDishPersistencePort dishPersistencePort(){
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(), categoryPersistencePort(), restaurantPersistencePort());
    }

    @Bean
    ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryMysqlAdapter(categoryEntityMapper, categoryRepository);
    }

    @Bean
    ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    IOrderPersistencePort orderPersistencePort(){
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper, orderDishEntityMapper, orderDishRepository);
    }

    @Bean
    IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), restaurantPersistencePort(), dishPersistencePort());
    }
}
