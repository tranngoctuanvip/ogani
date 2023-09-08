package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Repository.OrderRepository;
import com.example.ogani_be.Service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Map<String, Object>> sumOrders(LocalDate startTime, LocalDate endTime) {
        validateTime(startTime,endTime);
        var sumOrder = orderRepository.selectTotal(startTime,endTime);
        return sumOrder;
    }
    private void validateTime(LocalDate startTime, LocalDate endTime){
        if (startTime == null && endTime != null){
            throw new RuntimeException("Chưa chọn ngày bắt đầu!");
        }
        if (startTime != null && endTime == null){
            throw new RuntimeException("Chưa chọn ngày kết thúc");
        }
    }
}
