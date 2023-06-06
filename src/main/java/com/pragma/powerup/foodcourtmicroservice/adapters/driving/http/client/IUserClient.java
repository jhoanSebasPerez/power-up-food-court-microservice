package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import java.util.Map;

@FeignClient(name = "USER-SERVICE")
@Headers("Authorization: {token}")
public interface IUserClient {

    @RequestLine("GET /user-service/user/{userId}/is-owner")
    Map<String, Boolean> isOwner(
            @Param("token")String token,
            @Param("userId") String userDni);


    @RequestLine("GET /user-service/employee/{employeeDni}/find-restaurant")
    Map<String, Long> findRestaurantIdByDni(
            @Param("token")String token,
            @Param("employeeDni") String employeeDni);
}
