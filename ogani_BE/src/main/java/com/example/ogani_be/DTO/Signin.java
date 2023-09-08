package com.example.ogani_be.DTO;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class Signin {
    private String username;
    private String password;
    private String token;
    private String type = "Bearer";
    private Collection<? extends GrantedAuthority> role;
}
