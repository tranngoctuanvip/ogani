package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Entity.Chatbot;
import com.example.ogani_be.Repository.ChatbotRepository;
import com.example.ogani_be.Service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatbotServiceImpl implements ChatbotService {
    @Autowired
    private ChatbotRepository chatbotRepository;
    @Autowired
    private Mapper mapper;
    @Override
    public Chatbot create(Chatbot chatbot) {
        Chatbot c = new Chatbot();
        c.setSender(mapper.getUserId());
        c.setReceiver(chatbot.getReceiver());
        c.setSentContent(chatbot.getSentContent());
        chatbotRepository.save(c);
        return c;
    }

    @Override
    public Chatbot update(Chatbot chatbot, Long id) {
        return null;
    }
}
