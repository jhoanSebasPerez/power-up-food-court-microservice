package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFound;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Dish;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 2;

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;

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

    @Override
    public boolean existAllDishesByRestaurant(Long idRestaurant, List<Long> dishesIds) {
        List<Object> dishesReturned = dishRepository
                .dishesExistsByRestaurant(idRestaurant, dishesIds);

        return dishesIds.size() == dishesReturned.size();
    }

    @Override
    public List<Dish> findAllDishes(Restaurant restaurant, Integer pageNumber, Integer pageSize){
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);

        List<DishEntity> dishEntities = dishRepository
                .findAllByRestaurantAndActiveTrue(restaurantEntity, pageRequest).getContent();

        return dishEntityMapper.toDishList(dishEntities);
    }

    @Override
    public List<Dish> findAllDishesByCategory(Restaurant restaurant, Category category, Integer pageNumber, Integer pageSize){
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);

        List<DishEntity> dishEntities = dishRepository
                .findAllByRestaurantAndCategoryAndActiveTrue(restaurantEntity, categoryEntity, pageRequest).getContent();

        return dishEntityMapper.toDishList(dishEntities);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int requestPageNumber;
        int requestPageSize;

        if(pageNumber != null && pageNumber > 0)
            requestPageNumber = pageNumber;
        else
            requestPageNumber = DEFAULT_PAGE_NUMBER;

        if(pageSize != null && pageSize > 0)
            requestPageSize = pageSize;
        else
            requestPageSize = DEFAULT_PAGE_SIZE;

        Sort sort = Sort.by(Sort.Order.asc("name"));

        return PageRequest.of(requestPageNumber - 1, requestPageSize, sort);
    }
}
