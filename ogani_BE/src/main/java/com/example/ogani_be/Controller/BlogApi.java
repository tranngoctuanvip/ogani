package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Mapper.PageMapper;
import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseDataTotal;
import com.example.ogani_be.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("blog")
public class BlogApi {
    @Autowired
    private BlogService blogService;
    @Autowired
    private PageMapper pageMapper;
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestParam String code, @RequestParam String content,
                                    @RequestParam MultipartFile image, @RequestParam String title){
        try {
            var create = blogService.create(code, content, image, title);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam String code, @RequestParam String content,
                                    @RequestParam MultipartFile image, @RequestParam String title,
                                    @RequestParam Integer status, @Param("id") Long id){
        try {
            var update = blogService.update(code, content, image, title,status,id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Update Successfull").data(update).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getBlog")
    public ResponseEntity<?> getBlog(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer size,
                                     @RequestParam(defaultValue = "desc") String sortType,
                                     @RequestParam(defaultValue = "id") String sortBy){
        try {
            var pageable = pageMapper.pageable(page,size,sortType,sortBy);
            var getList = blogService.getAll(pageable);
            var pageBlog = new PageImpl<>(getList);
            var data = pageBlog.getContent();
            var total = pageBlog.getTotalElements();
            return new ResponseEntity<>(ResponseDataTotal.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(data).total(total).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("selectTop3")
    public ResponseEntity<?> selectTop3(){
        try {
            var selectTop3 = blogService.selectTop3();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(selectTop3).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("delete")
    public ResponseEntity<?> delete(@Param("id") Long id){
        try {
             blogService.delete(id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Delete Successfull").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
