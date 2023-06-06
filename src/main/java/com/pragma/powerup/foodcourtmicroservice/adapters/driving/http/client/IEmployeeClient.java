package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(name = "EMPLOYEE-SERVICE")
@Headers("Authorization: {token}")
public interface IEmployeeClient {

    @RequestLine("GET /user-service/employee/{employeeDni}/find-restaurant")
    Map<String, Long> findRestaurantIdByDni(
            @Param("token")String token,
            @Param("employeeDni") String employeeDni);
}
