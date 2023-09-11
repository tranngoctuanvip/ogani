package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.DTO.CategoryDto;
import com.example.ogani_be.Entity.Category;
import com.example.ogani_be.Repository.CategoryRepository;
import com.example.ogani_be.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private final Mapper mapper;
    @Override
    public Category create(CategoryDto categoryDto) {
        validate(categoryDto);
        Category category = new Category();
        category.setCode(categoryDto.getCode());
        category.setName(categoryDto.getName());
        category.setCreateBy(mapper.getUserId());
        category.setCreateAt(LocalDateTime.now());
        category.setDeleted(Constant.NOTDELETE);
        category.setStatus(categoryDto.getStatus());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category update(CategoryDto categoryDto, Long id) {
        validate(categoryDto);
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (categoryOptional.isEmpty()){
            throw new RuntimeException("Không tồn tại danh mục");
        }
        Category category = categoryOptional.get();
        category.setCode(categoryDto.getCode());
        category.setName(categoryDto.getName());
        category.setUpdateBy(mapper.getUserId());
        category.setStatus(categoryDto.getStatus());
        category.setUpdateAt(LocalDateTime.now());
        return category;
    }

    @Override
    public List<Category> getAll() {
        var getAll = categoryRepository.findAll();
        return getAll;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (optionalCategory.isEmpty()){
            throw new RuntimeException("Không tồn tại danh mục");
        }
        Category category = optionalCategory.get();
        category.setDeleteAt(LocalDateTime.now());
        category.setDeleted(Constant.DELETE);
        categoryRepository.save(category);
    }

    private void validate(CategoryDto categoryDto){
        if (categoryDto.getCode() == null){
            throw new RuntimeException("Mã code không được để trống!");
        }
        if (categoryDto.getName() == null){
            throw new RuntimeException("Tên danh mục không được để trống");
        }
    }
}
