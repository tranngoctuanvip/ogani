package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.DTO.CommentDto;
import com.example.ogani_be.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("comment")
public class CommentApi {
    private final KafkaTemplate<String,CommentDto> kafkaTemplate;
    private final CommentService commentService;
    public CommentApi(KafkaTemplate<String, CommentDto> kafkaTemplate, CommentService commentService) {
        this.kafkaTemplate = kafkaTemplate;
        this.commentService = commentService;
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CommentDto commentDto){
        try {
            kafkaTemplate.send("comment",commentDto);
            var comment = commentService.comment();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Comment Successfull").data(comment).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
