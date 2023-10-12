package com.example.ogani_be.Service;

import com.example.ogani_be.Entity.Chatbot;

public interface ChatbotService {
    Chatbot create(Chatbot chatbot);
    Chatbot update(Chatbot chatbot, Long id);
}
