package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.DTO.CategoryDto;
import com.example.ogani_be.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("category")
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto){
        try {
            var create = categoryService.create(categoryDto);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto, @Param("id") Long id){
        try {
            var update = categoryService.update(categoryDto,id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Update Successfull").data(update).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        try {
            var getAll = categoryService.getAll();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(getAll).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("delete")
    public ResponseEntity<?> delete(@Param("id") Long id){
        try {
            categoryService.delete(id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Delete Successfull").build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getNameAndCode")
    public ResponseEntity<?> getNameAndCode(@RequestParam(value = "code", required = false) String code,
                                            @RequestParam(value = "name", required = false) String name){
        try {
            var getNameAndCode = categoryService.getByNameAndCode(code,name);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(getNameAndCode).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
