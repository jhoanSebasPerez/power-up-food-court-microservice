package com.pragma.powerup.foodcourtmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nit;
    private String address;
    private String phone;
    private String urlLogo;

    @Column(name = "owner_dni")
    private String ownerDni;

}
