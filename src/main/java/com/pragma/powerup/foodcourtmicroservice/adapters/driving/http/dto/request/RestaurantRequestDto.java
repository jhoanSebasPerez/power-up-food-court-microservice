package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantRequestDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String nit;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String urlLogo;

    @NotNull
    private Long customerId;
}
