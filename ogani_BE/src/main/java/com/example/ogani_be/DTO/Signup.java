package com.example.ogani_be.DTO;

import com.example.ogani_be.Entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Signup {
    private String userName;
    private String password;
    private String email;
    private String name;
    private Set<String> roles;
}
