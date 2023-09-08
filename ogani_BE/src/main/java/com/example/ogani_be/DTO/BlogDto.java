package com.example.ogani_be.DTO;

import lombok.Data;

@Data
public class BlogDto {
    private String code;
    private String title;
    private String content;
    private String image;
    private Integer status;
}
