package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Entity.ProductDetail;
import com.example.ogani_be.Repository.ProductDetailRepository;
import com.example.ogani_be.Service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;
    private final Mapper mapper;
    @Override
    public ProductDetail create(String title, Long productId, String content) {
        valid(title,content);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setTitle(title);
        productDetail.setProductId(productId);
        productDetail.setCreateBy(mapper.getUserId());
        productDetail.setCreateAt(LocalDateTime.now());
        productDetail.setDeleted(Constant.NOTDELETE);
        productDetail.setStatus(Constant.STATUS);
        productDetail.setContent(content);
        productDetailRepository.save(productDetail);
        return productDetail;
    }

    @Override
    public ProductDetail update(String title, String content, Long id) {
        valid(title,content);
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findByProductIdAndDeleted(id,Constant.NOTDELETE);
        if (productDetailOptional.isEmpty()){
            throw new RuntimeException("Chi tiết sản phẩm không tồn tại");
        }
        ProductDetail productDetail = productDetailOptional.get();
        productDetail.setUpdateAt(LocalDateTime.now());
        productDetail.setTitle(title);
        productDetail.setUpdateBy(mapper.getUserId());
        productDetail.setContent(content);
        productDetailRepository.save(productDetail);
        return productDetail;
    }

    @Override
    public void delete(Long id) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findByProductIdAndDeleted(id,Constant.NOTDELETE);
        if (productDetailOptional.isEmpty()){
            throw new RuntimeException("Chi tiết sản phẩm không tồn tại");
        }
        ProductDetail productDetail = productDetailOptional.get();
        productDetail.setDeleteAt(LocalDateTime.now());
        productDetail.setDeleted(Constant.DELETE);
    }
    private void valid(String title, String content){
        if (title == null){
            throw new RuntimeException("Tiêu đề không được để trống");
        }
        if (content == null){
            throw new RuntimeException("Nội dung không được để trống");
        }
    }
}
