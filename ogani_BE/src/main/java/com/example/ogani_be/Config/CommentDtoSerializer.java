package com.example.ogani_be.Config;

import com.example.ogani_be.DTO.CommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;


public class CommentDtoSerializer implements Serializer<CommentDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, CommentDto commentDto) {
        try {
            return objectMapper.writeValueAsBytes(commentDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
