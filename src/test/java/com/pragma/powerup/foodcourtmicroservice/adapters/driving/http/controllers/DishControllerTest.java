package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.discovery.converters.Auto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.handlers.impl.DishHandlerImpl;
import com.pragma.powerup.foodcourtmicroservice.configuration.Constants;
import com.pragma.powerup.foodcourtmicroservice.configuration.ControllerAdvisor;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.MainSecurity;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.jwt.JwtUtil;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DishController.class)
class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDishHandler dishHandler;

    @MockBean
    private DishController dishController;

    ObjectMapper objectMapper;

    //MockMvc mockMvc;

    DishRequestDto dishRequestDto;

    @BeforeEach
    void setUp() {
        //mockMvc = MockMvcBuilders.standaloneSetup(dishController, ControllerAdvisor.class).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        dishRequestDto = DishRequestDto.builder()
                .name("paella")
                .price(23000D)
                .description("delicious dish")
                .urlImage("url image about dish")
                .restaurantId(1L)
                .active(true)
                .categoryId(1L)
                .build();
    }

    @Test
    //@WithMockUser(roles = "OWNER")
    void createDishTest() throws Exception {
        MockHttpServletRequestBuilder request = post("/food-court/dish")
                .with(user("owner").roles("OWNER"))
                .header(HttpHeaders.AUTHORIZATION, "aoshd")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishRequestDto));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(String.format("$.%s", Constants.RESPONSE_MESSAGE_KEY))
                        .value(Constants.DISH_CREATED_MESSAGE));

        //verify(dishHandler).saveDish(anyString(), any(DishRequestDto.class));
    }

    @Test
    void createDishWithoutAuthorizationHeaderTest() throws Exception{
        MockHttpServletRequestBuilder request = post("/food-court/dish")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishRequestDto));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}