package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.DTO.CartDto;
import com.example.ogani_be.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("cart")
public class CartApi {
    @Autowired
    private CartService cartService;
    @PostMapping("create")
    public ResponseEntity<?> create(@Param("productId") Long productId){
        try {
            var create = cartService.create(productId);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Create Successfull").data(create).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getUnpaidCart")
    public ResponseEntity<?> getUnpaidCart(){
        try {
            var getUnpaid = cartService.getUnpaidCart();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(getUnpaid).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("deleteCart")
    public ResponseEntity<?> deleteCart(@Param("cartId") Long cartId){
        try {
            cartService.delete(cartId);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Delete Successfull").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("update")
    public ResponseEntity<?> updateStatus(@RequestBody CartDto cartDto, @Param("id") Long id){
        try{
            var updateStatus = cartService.update(cartDto,id);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Update Successfull").data(updateStatus).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("unpaid")
    public ResponseEntity<?> unpaid(){
        try{
            var unpaid = cartService.unpaid();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(unpaid).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
