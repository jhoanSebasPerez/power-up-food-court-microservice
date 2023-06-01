package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishUpdateDto {

    @NotNull
    @Positive
    private double price;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Long restaurantId;
}
