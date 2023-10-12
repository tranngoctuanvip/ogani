package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.PaymentDto;
import com.example.ogani_be.Entity.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    String URL();
    Payment create(PaymentDto paymentDto);
    Integer totalPayment();
}
