package com.pragma.powerup.foodcourtmicroservice.domain.model;

import java.util.Date;
import java.util.List;

public class Order {

    private Long id;
    private String clientId;
    private Date date;
    private OrderState state;
    private String chefDni;
    private Restaurant restaurant;
    private List<OrderDish> dishes;

    public Order(){}

    public Order(Long id, String clientId, Date date, OrderState state, String chefDni,
                 Restaurant restaurant, List<OrderDish> dishes) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.state = state;
        this.chefDni = chefDni;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getChefDni() {
        return chefDni;
    }

    public void setChefDni(String chefDni) {
        this.chefDni = chefDni;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }
}
