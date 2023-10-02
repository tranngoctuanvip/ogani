package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.DTO.CommentDto;
import com.example.ogani_be.Entity.Comment;
import com.example.ogani_be.Entity.User;
import com.example.ogani_be.Repository.CommentRepository;
import com.example.ogani_be.Repository.UserRepository;
import com.example.ogani_be.Service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Inject
    private  Mapper mapper;
    private String comment;
    @Override
    @KafkaListener(topics = "comment",groupId = "receive-consumer-group")
    public void listen(String commentDto) {
       comment = commentDto;
    }
    @SneakyThrows
    @Override
    public List<Comment> comment(){
        ObjectMapper objectMapper = new ObjectMapper();
        CommentDto commentJson = objectMapper.readValue(comment, CommentDto.class);
        List<User> userList = userRepository.findByRoleSet_CodeOrRoleSet_Code("ADMIN","STAFF");
        List<Comment> commentList = new ArrayList<>();
        for (int i=0;i<userList.size();i++){
            Comment comment = new Comment();
            comment.setCreateAt(LocalDateTime.now());
            comment.setName(commentJson.getName());
            comment.setEmail(commentJson.getEmail());
            comment.setUsernameId(mapper.getUserId());
            comment.setReceiveId(userList.get(i).getId());
            comment.setComment(commentJson.getComment());
            commentList.add(comment);
        }
        commentRepository.saveAll(commentList);
        return commentList;
    }

    @Override
    public void create(CommentDto commentDto) {

    }
}
