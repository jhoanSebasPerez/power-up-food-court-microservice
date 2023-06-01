package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.foodcourtmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Person already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> saveDish(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Validated @RequestBody DishRequestDto dishRequestDto){
        dishHandler.saveDish(token, dishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Update price and description",
            responses = {
                    @ApiResponse(responseCode = "200", description = "the dish has been updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map")))})
    @PutMapping("/{dishId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> updateDish(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable("dishId") Long dishId,
            @Validated @RequestBody DishUpdateDto dishUpdateDto) {
        dishHandler.updateDish(token, dishId, dishUpdateDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }

    @Operation(summary = "Enable/Disable dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "the dish has been enable/disable",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map")))})
    @PatchMapping("/{dishId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> enableDisable(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable("dishId") Long dishId,
            @Validated @RequestBody boolean enable) {
        boolean isEnable = dishHandler.enableDisable(token, dishId, enable);
        String result = isEnable ? "enable" : "disable";
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY, String.format("Dish has been %s", result)));
    }



}
