package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.Deliver;
import com.example.ogani_be.DTO.OrderDto;
import com.example.ogani_be.Entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order create(OrderDto orderDto);
    Order update(Deliver deliver, Long id);
    List<Map<String,Object>> getAll();
    void deleted(Long id);
}
