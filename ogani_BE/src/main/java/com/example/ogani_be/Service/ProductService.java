package com.example.ogani_be.Service;

import com.example.ogani_be.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product create(String code, String name, MultipartFile image, float price, Integer quantity, String content,
                   Long categoryId);
    List<Product> getAll();
    List<Map<String,Object>> lastestProduct();
    Product update(String code, String name, MultipartFile image, float price, Integer quantity, String content,
                   Long categoryId, Long id);
    void delete(Long id);
    List<Map<String,Object>> getProduct(Pageable pageable);
}
