package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.ICategoryHandler;
import com.pragma.powerup.foodcourtmicroservice.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;

    @Override
    public boolean existById(Long categoryId) {
        return categoryServicePort.existById(categoryId);
    }
}
