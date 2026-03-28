package com.pushpey.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import com.pushpey.ecommerce.entity.Role;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;

    @Column(unique = true)
    private  String email ;

    @JsonIgnore
    private String password ;

    @Enumerated(EnumType.STRING)
    private Role role ;

    private boolean isActive = true ;


}
