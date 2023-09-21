package com.example.ogani_be.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private String status;
    private String message;
    private String URL;
}
