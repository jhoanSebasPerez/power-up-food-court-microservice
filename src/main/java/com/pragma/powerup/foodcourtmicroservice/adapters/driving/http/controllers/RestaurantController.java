package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantItemDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Add a new restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Map<String, String>> saveRestaurant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Validated @RequestBody RestaurantRequestDto restaurantRequestDto){
        restaurantHandler.saveRestaurant(token, restaurantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.RESTAURANT_CREATED_MESSAGE));
    }

    @Operation(summary = "Get restaurant by id",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/{restaurantId}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<RestaurantResponseDto> getRestaurant(@PathVariable(name = "restaurantId")Long restaurantId){
        RestaurantResponseDto response = restaurantHandler.findById(restaurantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List restaurant by pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of restaurants",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    })
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<List<RestaurantItemDto>> getRestaurant(@RequestParam(required = false) Integer pageNumber,
                                                          @RequestParam(required = false) Integer pageSize){
        List<RestaurantItemDto> response = restaurantHandler.listRestaurant(pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }
}
