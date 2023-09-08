package com.example.ogani_be.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<Integer> cartId;
    private Integer status;
    private String address;
    private String phone;
    private String userName;
}
