package com.example.ogani_be.Service;

import com.example.ogani_be.Entity.Cart;

import java.util.List;
import java.util.Map;

public interface CartService {
    Cart create(Long productId);
    List<Map<String,Object>> getUnpaidCart();
    void delete(Long id);
}
