package com.pragma.powerup.foodcourtmicroservice.domain.api;

import com.pragma.powerup.foodcourtmicroservice.domain.model.Category;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    boolean existById(Long categoryId);
}
