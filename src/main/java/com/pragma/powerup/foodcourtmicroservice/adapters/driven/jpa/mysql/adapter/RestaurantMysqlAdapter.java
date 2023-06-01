package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFound;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.projections.IRestaurantItemView;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 2;

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public Restaurant findById(Long idRestaurant) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(idRestaurant);
        if(restaurant.isEmpty())
            throw new RestaurantNotFound();
        return restaurantEntityMapper.toModel(restaurant.get());
    }

    @Override
    public List<Restaurant> findAllRestaurant(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        List<IRestaurantItemView> list = restaurantRepository.findAllRestaurant(pageRequest).getContent();
        return list.stream().map(iRestaurantItemView -> {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(iRestaurantItemView.getName());
            restaurant.setUrlLogo(iRestaurantItemView.getUrlLogo());
            return restaurant;
        }).toList();
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
