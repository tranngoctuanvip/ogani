package com.example.ogani_be.Service;

import com.example.ogani_be.Entity.ProductDetail;

public interface ProductDetailService {
    ProductDetail create(String title,Long productId, String content);
    ProductDetail update(String title, String content, Long id);
    void delete(Long id);
}
