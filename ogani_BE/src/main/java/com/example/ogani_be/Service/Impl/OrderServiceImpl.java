package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Common.Utils.Utils;
import com.example.ogani_be.DTO.Deliver;
import com.example.ogani_be.DTO.OrderDto;
import com.example.ogani_be.Entity.*;
import com.example.ogani_be.Repository.*;
import com.example.ogani_be.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final Mapper mapper;
    @Override
    public Order create(OrderDto orderDto) {
        valid(orderDto);
        Utils.validatePhoneNumber(orderDto.getPhone());
        Order order = new Order();
        order.setCreateAt(LocalDateTime.now());
        order.setDeleted(Constant.NOTDELETE);
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setCreateBy(mapper.getUserId());
        order.setUserName(orderDto.getUserName());
        order.setStatus(Constant.PROCESSING);
        orderRepository.save(order);
        for (int i=0;i<orderDto.getCartId().size();i++){
            orderRepository.create(orderDto.getCartId().get(i),order.getId());
        }
        return order;
    }

    @Override
    public Order update(Deliver deliver, Long id) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (optionalOrder.isEmpty()){
            throw new RuntimeException("Đơn hàng không tồn tại");
        }
        Order order = optionalOrder.get();
        order.setUpdateBy(mapper.getUserId());
        order.setDeliver(deliver.getDeliver());
        order.setStartTime(deliver.getStartTime());
        order.setEndTime(deliver.getEndTime());
        order.setStatus(deliver.getStatus());
        orderRepository.save(order);
        for (int i=0;i<order.getCartList().size();i++){
            Optional<Cart> cartOptional = cartRepository.findByIdAndDeleted(order.getCartList().get(i).getId(),Constant.NOTDELETE);
            Optional<Product> productOptional = productRepository.findByIdAndDeleted(cartOptional.get().getProductId(), Constant.NOTDELETE);
            if (order.getStatus() == Constant.DELIVERED){
                Product product = productOptional.get();
                if(product.getQuantity() - cartOptional.get().getQuality() >=0){
                    product.setQuantity(product.getQuantity()-cartOptional.get().getQuality());
                    productRepository.save(product);
                }
                else {
                    throw new RuntimeException("Số lượng sản phẩm không đủ");
                }
            }
        }
        return order;
    }

    @Override
    public List<Map<String, Object>> getAll() {
        Optional<User> userOptional = userRepository.findByIdAndDeleted(mapper.getUserId(), Constant.NOTDELETE);
        User user = userOptional.get();
        var roleId = user.getRoleSet().stream().map(Role::getId).collect(Collectors.toList());
        for (Long role : roleId){
            Optional<Role> roleOptional = roleRepository.findById(role);
            Role role1 = roleOptional.get();
            if (role1.getCode().equals("ADMIN") || role1.getCode().equals("STAFF")){
              return orderRepository.getAll();
            }
            else if (role1.getCode().equals("SHIPPER")){
               return orderRepository.getAllByDeliver(mapper.getUserId());
            }
        }
        return null;
    }

    @Override
    public void deleted(Long id) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (optionalOrder.isEmpty()){
            throw new RuntimeException("Không tồn tại đơn hàng");
        }
        orderRepository.removeById(id);
    }
    private void valid(OrderDto orderDto){
        if (orderDto.getAddress() == null){
            throw new RuntimeException("Địa chỉ không được để trống");
        }
        if (orderDto.getUserName() == null){
            throw new RuntimeException("Tên không được để trống");
        }
        if (orderDto.getPhone() == null){
            throw new RuntimeException("Số điện thoại không được để trống");
        }
    }
}
