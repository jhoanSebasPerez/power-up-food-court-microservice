package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RestaurantRequestDto {

    @NotNull
    @NotBlank
    //@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "numbers only are not allowed")
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "Only accepts numbers")
    private String nit;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^\\+[0-9]+$", message = "Include country code")
    private String phone;

    @NotNull
    @NotBlank
    private String urlLogo;

    @NotNull
    private String ownerDni;
}
