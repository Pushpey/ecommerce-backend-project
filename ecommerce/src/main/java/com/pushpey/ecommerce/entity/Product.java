package com.pushpey.ecommerce.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;

    private double price ;

    private int stock ;

    private String description ;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller ;
}
