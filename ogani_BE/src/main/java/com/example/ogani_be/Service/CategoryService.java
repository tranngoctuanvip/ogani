package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.CategoryDto;
import com.example.ogani_be.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(CategoryDto categoryDto);
    Category update(CategoryDto categoryDto, Long id);
    List<Category> getAll();
    void delete(Long id);
}
