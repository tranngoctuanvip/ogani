package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Mapper.PageMapper;
import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseDataTotal;
import com.example.ogani_be.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("product")
public class ProductApi {
    @Autowired
    private ProductService productService;
    @Autowired
    private PageMapper pageMapper;
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestParam("code") String code, @RequestParam("name") String name,
                                    @RequestParam MultipartFile image,@RequestParam float price,
                                    @RequestParam Integer quantity,@RequestParam String content,@RequestParam List<Long> categoryId){
        try {
            var create = productService.create(code, name, image, price, quantity, content, categoryId);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        try {
            var getAll = productService.getAll();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(getAll).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("lastestProduct")
    public ResponseEntity<?> lastestProduct(){
        try {
            var lastestProduct = productService.lastestProduct();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(lastestProduct).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getProduct")
    public ResponseEntity<?> getProduct(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "6") Integer size,
                                        @RequestParam(defaultValue = "desc") String sortType,
                                        @RequestParam(defaultValue = "id") String sortBy){
        try {
            var pageable = pageMapper.pageable(page,size,sortType,sortBy);
            var getProduct = productService.getProduct(pageable);
            var pages = new PageImpl<>(getProduct);
            var total = pages.getTotalElements();
            var data = pages.getContent();
            return new ResponseEntity<>(ResponseDataTotal.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(data).total(total).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
