package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.Message;

public interface KafkaConsumerService {
    void listen(String message);
    void message();
}
