package com.example.ogani_be.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto {
    private String paymentCode;
    private Integer money;
    private String description;
    private String errorCode;
    private String transactionCode;
    private String bankCode;
    private Long orderId;
}
