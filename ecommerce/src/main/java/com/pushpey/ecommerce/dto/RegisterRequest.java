package com.pushpey.ecommerce.dto;

import com.pushpey.ecommerce.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // USER / ADMIN / SELLER
}