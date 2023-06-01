package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFound;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void updateDish(Dish dish) {
        Optional<DishEntity> dishOptional = dishRepository.findById(dish.getId());

        if(dishOptional.isEmpty())
            throw new DishNotFound();

        DishEntity dishEntity = dishOptional.get();
        dishEntity.setPrice(dish.getPrice());
        dishEntity.setDescription(dish.getDescription());

        dishRepository.save(dishEntity);
    }

    @Override
    public boolean enableDisable(Dish dish, boolean enable) {
        dish.setActive(enable);
        return dishRepository.save(dishEntityMapper.toEntity(dish)).getActive();
    }

    @Override
    public Dish findById(Long id) {
        Optional<DishEntity> dishOptional = dishRepository.findById(id);

        if(dishOptional.isEmpty())
            throw new DishNotFound();

        return dishEntityMapper.toModel(dishOptional.get());
    }
}
