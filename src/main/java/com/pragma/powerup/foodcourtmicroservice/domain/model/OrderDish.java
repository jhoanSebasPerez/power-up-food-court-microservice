package com.pragma.powerup.foodcourtmicroservice.domain.model;

public class OrderDish {

    private Dish dish;
    private Integer quantity;

    private Order order;

    public OrderDish(){}

    public OrderDish(Dish dish, Integer quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
