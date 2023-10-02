package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.CommentDto;
import com.example.ogani_be.Entity.Comment;

import java.util.List;

public interface CommentService {
    void create(CommentDto commentDto);
    void listen(String commentDto);
    List<Comment> comment();
}
