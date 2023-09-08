package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.DTO.OrderDto;
import com.example.ogani_be.Entity.Cart;
import com.example.ogani_be.Entity.Order;
import com.example.ogani_be.Entity.Product;
import com.example.ogani_be.Repository.CartRepository;
import com.example.ogani_be.Repository.OrderRepository;
import com.example.ogani_be.Repository.ProductRepository;
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
    private final Mapper mapper;
    @Override
    public Order create(OrderDto orderDto) {
        Order order = new Order();
        order.setCreateAt(LocalDateTime.now());
        order.setDeleted(Constant.NOTDELETE);
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setCreateBy(mapper.getUserId());
        order.setUserName(orderDto.getUserName());
        order.setStatus(Constant.UNPAID);
        orderRepository.save(order);
        for (int i=0;i<orderDto.getCartId().size();i++){
            orderRepository.create(orderDto.getCartId().get(i),order.getId());
        }
        return order;
    }

    @Override
    public Order update(Integer status, Long id) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        Order order = optionalOrder.get();
        order.setUpdateBy(mapper.getUserId());
        order.setStatus(status);
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
        var getAll = orderRepository.getAll();
        return getAll;
    }

    @Override
    public void deleted(Long id) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (optionalOrder.isEmpty()){
            throw new RuntimeException("Không tồn tại đơn hàng");
        }
        orderRepository.removeById(id);
    }
}
