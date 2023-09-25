package com.example.ogani_be.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private Integer status;
    private List<RoleDto> roleDtos;
}

