package com.example.ogani_be.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private String code;
    private String name;
    private String image;
    private float price;
    private Integer quantity;
    private Integer status;
    private List<Long> categoryId;
}
