//package com.example.ogani_be.Controller;
//
//import com.example.ogani_be.Common.Response.ResponseData;
//import com.example.ogani_be.Common.Response.ResponseDataStatus;
//import com.example.ogani_be.Common.Response.ResponseError;
//import com.example.ogani_be.DTO.Message;
//import com.example.ogani_be.Service.Impl.KafkaConsumerServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
//import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;
//
//@RestController
//@RequestMapping("kafka")
//public class KafkaApi {
//    private final KafkaTemplate<String,String> kafkaTemplate;
//    private final ObjectMapper objectMapper;
//    private final KafkaConsumerServiceImpl kafkaConsumerService;
//    public KafkaApi(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, KafkaConsumerServiceImpl kafkaConsumerService) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.objectMapper = objectMapper;
//        this.kafkaConsumerService = kafkaConsumerService;
//    }
//
//    @PostMapping("message")
//    public ResponseEntity<?> message(@RequestBody String message){
//        try{
//            kafkaTemplate.send("test1",message);
//            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
//                    .message("Send Message Successfull").data(message).build(),HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
//                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
////    @SneakyThrows
////    @GetMapping("receive")
////    public ResponseEntity<?> receive(){
////        Message message = new Message(kafkaConsumerService.receivedMessage);
////       return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message),HttpStatus.OK);
////    }
//
//    @GetMapping("getMessage")
//    public ResponseEntity<?> getMessage(){
//        try {
//            kafkaConsumerService.message();
//            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
//                    .message("Get Message Successfull").build(),HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
//                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
//        }
//    }
//}
