package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Make a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, String>> saveOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Validated @RequestBody OrderRequestDto orderRequestDto){
        orderHandler.saveOrder(token, orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CREATED_MESSAGE));
    }

    @Operation(summary = "List orders by state and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
            })
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    ResponseEntity<List<OrderResponseDto>> findAllByState(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(required = true) String state,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize){
        List<OrderResponseDto> response = orderHandler.findAllByRestaurantAndState(token, state, pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List orders by state and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
            })
    @PutMapping("change-to-in-preparation")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    ResponseEntity<Map<String, String>> assignToOrderAndChangeToInPreparation(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody List<Long> orders){
        orderHandler.assignToOrderAndChangeToInPreparation(token, orders);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CHANGE_IN_PROCESS));
    }
}
