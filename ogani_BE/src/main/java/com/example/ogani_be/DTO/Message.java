package com.example.ogani_be.DTO;

import lombok.Data;

@Data
public class Message {
    private String message;

    public Message(String receivedMessage) {
        this.message = receivedMessage;
    }
}
