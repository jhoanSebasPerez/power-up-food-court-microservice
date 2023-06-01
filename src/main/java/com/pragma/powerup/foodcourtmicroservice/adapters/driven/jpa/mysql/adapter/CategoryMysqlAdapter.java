package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;
import com.pragma.powerup.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public boolean existById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }


}
