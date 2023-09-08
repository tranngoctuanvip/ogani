package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Common.Utils.Utils;
import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Entity.Blog;
import com.example.ogani_be.Repository.BlogRepository;
import com.example.ogani_be.Service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    private final Utils utils;
    private final Mapper mapper;

    @Override
    public Blog create(String code, String content, MultipartFile image, String title) {
        Blog blog = new Blog();
        utils.isValid(image);
        blog.setImage(utils.imageName(image));
        blog.setCode(code);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setCreateAt(LocalDateTime.now());
        blog.setDeleted(Constant.NOTDELETE);
        blog.setStatus(Constant.STATUS);
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public Blog update(String code, String content, MultipartFile image, String title, Integer status, Long id) {
        Optional<Blog> blogOptional = blogRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (blogOptional.isEmpty()){
            throw new RuntimeException("Blog không tồn tại");
        }
        utils.isValid(image);
        Blog blog = blogOptional.get();
        blog.setImage(utils.imageName(image));
        blog.setTitle(title);
        blog.setCreateBy(mapper.getUserId());
        blog.setUpdateAt(LocalDateTime.now());
        blog.setContent(content);
        blog.setStatus(status);
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public List<Map<String,Object>> getAll(Pageable pageable) {
        var getAll = blogRepository.getAll(pageable);
        return getAll;
    }

    @Override
    public List<Map<String, Object>> selectTop3() {
        var selectTop3 = blogRepository.selectTop3();
        if (selectTop3.isEmpty()){
            throw new RuntimeException("Không tồn tại blog");
        }
        return selectTop3;
    }

    @Override
    public void delete(Long id) {
        Optional<Blog> blogOptional = blogRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (blogOptional.isEmpty()){
            throw new RuntimeException("Bài viết không tồn tại");
        }
        Blog blog = blogOptional.get();
        blog.setDeleteAt(LocalDateTime.now());
        blog.setDeleted(Constant.DELETE);
        blogRepository.save(blog);
    }
}
