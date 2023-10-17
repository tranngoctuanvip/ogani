package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.DTO.DialogflowHelper;
import com.example.ogani_be.Entity.Chatbot;
import com.example.ogani_be.Service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("chat")
public class ChatbotApi {
    @Autowired
    private ChatbotService chatbotService;
    private DialogflowHelper dialogflowHelper;
    @PostMapping("send")
    public ResponseEntity<?> create(@RequestBody Chatbot chatbot){
        try{
            var create = chatbotService.create(chatbot);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }

    public ChatbotApi() {
        dialogflowHelper = new DialogflowHelper("src/main/resources/tuantran-gdlc-105bc9848e04.json", "tuantran-gdlc");
    }

    @PostMapping("/bot")
    public String chat(@RequestBody String query) {
        String sessionId = dialogflowHelper.generateSessionId();
        String response = dialogflowHelper.detectIntent(sessionId, query);
        return response;
    }
}
