package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.Entity.Chatbot;
import com.example.ogani_be.Service.ChatbotService;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("chat")
public class ChatbotApi {
    @Autowired
    private ChatbotService chatbotService;
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

    @PostMapping("/bot")
    public String chat(@RequestBody String message) {
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            String projectId = "tuantran-gdlc";
            String sessionId = UUID.randomUUID().toString();
            String languageCode = "English — en";

            SessionName session = SessionName.of(projectId, sessionId);
            TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

            QueryResult queryResult = response.getQueryResult();
            String botResponse = queryResult.getFulfillmentText();

            return botResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Bot: Sorry, an error occurred.";
        }
    }
}
