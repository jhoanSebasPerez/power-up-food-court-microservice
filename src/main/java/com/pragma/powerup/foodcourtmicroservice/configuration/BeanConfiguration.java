package com.pragma.powerup.foodcourtmicroservice.configuration;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.DishUseCase;
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

    @Bean
    IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantMysqlAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
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
}
