package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
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
}
