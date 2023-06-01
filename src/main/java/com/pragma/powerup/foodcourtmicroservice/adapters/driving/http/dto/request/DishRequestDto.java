package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishRequestDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @NotBlank
    private String urlImage;

    private Boolean active;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long restaurantId;
}
