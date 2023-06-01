package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.foodcourtmicroservice.configuration.Constants;
import com.pragma.powerup.foodcourtmicroservice.configuration.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    IRestaurantHandler restaurantHandler;

    ObjectMapper objectMapper;

    MockMvc mockMvc;

    RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController, ControllerAdvisor.class).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        restaurantRequestDto = RestaurantRequestDto.builder()
                .name("girona")
                .address("avenue 10 with street 11")
                .nit("0348023")
                .phone("+571234567890")
                .urlLogo("osndojsndojvn")
                .ownerDni("32482309")
                .build();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void createRestaurantTest() throws Exception {
        MockHttpServletRequestBuilder request = post("/food-court/restaurant")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantRequestDto));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(String.format("$.%s", Constants.RESPONSE_MESSAGE_KEY))
                        .value(Constants.RESTAURANT_CREATED_MESSAGE));

        verify(restaurantHandler).saveRestaurant(any(RestaurantRequestDto.class));
    }

    @Test
    void createRestaurantWithUserRoleTest() throws Exception{
        MockHttpServletRequestBuilder request = post("/food-court/restaurant")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantRequestDto));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());
    }

}