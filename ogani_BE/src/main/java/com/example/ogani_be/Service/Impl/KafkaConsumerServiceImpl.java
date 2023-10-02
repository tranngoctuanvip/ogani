//package com.example.ogani_be.Service.Impl;
//
//import com.example.ogani_be.Common.Constant.Constant;
//import com.example.ogani_be.Common.Mapper.Mapper;
//import com.example.ogani_be.DTO.Message;
//import com.example.ogani_be.Entity.Order;
//import com.example.ogani_be.Entity.User;
//import com.example.ogani_be.Repository.OrderRepository;
//import com.example.ogani_be.Repository.UserRepository;
//import com.example.ogani_be.Service.KafkaConsumerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class KafkaConsumerServiceImpl implements KafkaConsumerService {
//    @Autowired
//    private Mapper mapper;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//    public String receivedMessage;
//    @Override
//    @KafkaListener(topics = "test1",groupId = "my-consumer-group")
//    public void listen(String message) {
//        System.out.println("receive: " +message);
//        receivedMessage = message;
//    }
//
//    @Override
//    public void message() {
//        Optional<User> userOptional = userRepository.findByIdAndDeleted(mapper.getUserId(), Constant.NOTDELETE);
//        if (userOptional.isEmpty()){
//            throw new RuntimeException("Không tồn tại người dùng");
//        }
//        Optional<Order> optionalOrder = orderRepository.findByDeliverAndDeleted(mapper.getUserId(), Constant.NOTDELETE);
//        Order order = optionalOrder.get();
//        if (order.getStatus() == Constant.PROCESSING) {
//            order.setNotification(receivedMessage);
//            orderRepository.save(order);
//        }
//    }
//}
