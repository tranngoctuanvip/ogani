package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.Service.StatisticalService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("statistical")
public class StatisticalApi {
    @Autowired
    private StatisticalService statisticalService;
    @GetMapping("sumOrder")
    public ResponseEntity<?> sumOrder(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      @JsonFormat(pattern = "yyyy-MM-dd")
                                      @RequestParam(required = false) LocalDate startTime,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      @JsonFormat(pattern = "yyyy-MM-dd")
                                      @RequestParam(required = false) LocalDate endTime){
        try {
            var sumOrder = statisticalService.sumOrders(startTime,endTime);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(sumOrder).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
