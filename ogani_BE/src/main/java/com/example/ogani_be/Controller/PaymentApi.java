package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.Config.Config;
import com.example.ogani_be.Service.PaymentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("payment")
public class PaymentApi {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("createPayment")
    public ResponseEntity<?> create(){
        try {
            var paymentUrl = paymentService.URL();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Payment Successfull").data(paymentUrl).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("totalPayment")
    public ResponseEntity<?> totalPayment(){
        try{
            var totalPayment = paymentService.totalPayment();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get total payment").data(totalPayment).build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.OK);
        }
    }
}
