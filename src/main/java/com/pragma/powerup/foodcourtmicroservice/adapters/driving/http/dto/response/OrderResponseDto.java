package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.foodcourtmicroservice.domain.model.OrderState;
import com.pragma.powerup.foodcourtmicroservice.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String clientId;
    private Date date;
    private OrderState state;
    private String chefDni;
    private Restaurant restaurant;
    private List<OrderDishResponseDto> dishes;
}
