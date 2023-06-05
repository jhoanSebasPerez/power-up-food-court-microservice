package com.pragma.powerup.foodcourtmicroservice.domain.spi;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;

public interface ICategoryPersistencePort {

    void saveCategory(Category category);

    boolean existById(Long categoryId);

    Category findByName(String categoryName);
}
