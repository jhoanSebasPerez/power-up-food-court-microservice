package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseDto {

    private DishResponseDto dish;
    private Integer quantity;

}
