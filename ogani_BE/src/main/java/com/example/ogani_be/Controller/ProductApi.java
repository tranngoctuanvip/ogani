package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Mapper.PageMapper;
import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseDataTotal;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                    @RequestParam Integer quantity,@RequestParam String content,@RequestParam Long categoryId){
        try {
            var create = productService.create(code, name, image, price, quantity, content, categoryId);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        try {
            var getNewProduct = productService.getNewProduct();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(getNewProduct).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
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
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getProduct")
    public ResponseEntity<?> getProduct(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "6") Integer size,
                                        @RequestParam(defaultValue = "desc") String sortType,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @Param("categoryId") Integer categoryId,
                                        @Param("name") String name
                                        ){
        try {
            var pageable = pageMapper.pageable(page,size,sortType,sortBy);
            var getProduct = productService.getProduct(name,categoryId,pageable);
            var pages = new PageImpl<>(getProduct);
            var total = pages.getTotalElements();
            var data = pages.getContent();
            return new ResponseEntity<>(ResponseDataTotal.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(data).total(total).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("delete")
    public ResponseEntity<?> delete(@Param("id") Long id){
        try {
            productService.delete(id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Delete Successfull").build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam("code") String code, @RequestParam("name") String name,
                                    @RequestParam MultipartFile image,@RequestParam float price,
                                    @RequestParam Integer quantity,@RequestParam String content,
                                    @RequestParam Long categoryId, @Param("id") Long id){
        try {
                var update = productService.update(code,name,image,price,quantity,content,categoryId,id);
                return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                        .message("Update Successfull").data(update).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@Param("id") Long id){
        try {
            var findById = productService.findById(id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Find Successfull").data(findById).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
