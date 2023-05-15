package com.pragma.powerup.foodcourtmicroservice.configuration;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.foodcourtmicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Bean
    IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantMysqlAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

}
