package com.example.ogani_be.Service;

import com.example.ogani_be.Entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog create(String code, String content, MultipartFile image, String title);
    Blog update(String code, String content, MultipartFile image, String title, Integer status, Long id);
    List<Map<String,Object>> getAll(Pageable pageable);
    List<Map<String, Object>> selectTop3();
    void delete(Long id);
}
