package com.example.ogani_be.DTO;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Struct;

import java.io.FileInputStream;
import java.util.UUID;

public class DialogflowHelper {
    private SessionsClient sessionsClient;
    private String projectId;
    public DialogflowHelper(String credentialsPath, String projectId) {
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
            sessionsClient = SessionsClient.create(SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build());
            this.projectId = projectId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String detectIntent(String sessionId, String query) {
        try {
            SessionName session = SessionName.of(projectId, sessionId);
            TextInput.Builder textInput = TextInput.newBuilder().setText(query).setLanguageCode("en-US");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            Struct struct = response.getQueryResult().getFulfillmentMessages(0).getPayload();
            return struct.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
