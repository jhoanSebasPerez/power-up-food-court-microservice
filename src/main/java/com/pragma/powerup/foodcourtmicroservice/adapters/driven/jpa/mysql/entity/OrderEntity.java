package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_dni")
    private String clientId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String state;

    //Restaurant employee
    @Column(name = "chef_dni")
    private String chefDni;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurant;


    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<OrderDishEntity> dishes;
}
