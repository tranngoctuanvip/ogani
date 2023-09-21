package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Common.Utils.Utils;
import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Entity.Product;
import com.example.ogani_be.Repository.ProductRepository;
import com.example.ogani_be.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    private final Mapper mapper;
    private final ProductDetailServiceImpl productDetail;
    @Override
    public Product create(String code, String name, MultipartFile image, float price, Integer quantity, String content,
                          Long categoryId) {
        valid(code,name,quantity,content,categoryId);
        Product product = new Product();
        Utils.isValid(image);
        Utils.checkSize(image);
        product.setCreateAt(LocalDateTime.now());
        product.setCode(code);
        product.setName(name);
        product.setPrice(price);
        product.setCreateBy(mapper.getUserId());
        product.setQuantity(quantity);
        product.setStatus(Constant.STATUS);
        product.setDeleted(Constant.NOTDELETE);
        product.setImage(Utils.imageName(image));
        productRepository.save(product);
        productRepository.create(categoryId, product.getId());
        productDetail.create(product.getName(), product.getId(),content);
        return product;
    }

    @Override
    public List<Product> getNewProduct() {
        var getNewProduct = productRepository.getNewProduct();
        return getNewProduct;
    }

    @Override
    public List<Map<String, Object>> lastestProduct() {
        var lastestProduct = productRepository.lastestProduct();
        if (lastestProduct.isEmpty()){
            throw new RuntimeException("Không tồn tại sản phẩm mới nhất");
        }
        return lastestProduct;
    }

    @Override
    public Product update(String code, String name, MultipartFile image, float price, Integer quantity,
                          String content, Long categoryId, Long id) {
        valid(code,name,quantity,content,categoryId);
        Utils.checkSize(image);
        Optional<Product> productOptional = productRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        if (productOptional.isEmpty()){
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        Product product = productOptional.get();
        Utils.isValid(image);
        product.setUpdateAt(LocalDateTime.now());
        product.setCode(code);
        product.setName(name);
        product.setPrice(price);
        product.setUpdateBy(mapper.getUserId());
        product.setQuantity(quantity);
        product.setStatus(Constant.STATUS);
        product.setDeleted(Constant.NOTDELETE);
        product.setImage(Utils.imageName(image));
        productRepository.save(product);
        productRepository.update(categoryId, product.getId());
        productDetail.update(product.getName(),content,product.getId());
        return product;
    }

    @Override
    public void delete(Long id) {
        Optional<Product> productOptional = productRepository.findByIdAndDeleted(id,Constant.NOTDELETE);
        Product product = productOptional.get();
        product.setDeleteAt(LocalDateTime.now());
        product.setDeleted(Constant.DELETE);
        productRepository.save(product);
        productDetail.delete(product.getId());
    }

    @Override
    public List<Map<String, Object>> getProduct(String name, Integer categoryId, Pageable pageable) {
        var getProduct = productRepository.getProduct(categoryId,name,pageable);
        return getProduct;
    }

    @Override
    public List<Map<String,Object>> findById(Long id) {
        var findById = productRepository.findByIdProduct(id);
        return findById;
    }

    private void valid(String code, String name, Integer quantity, String content,
                       Long categoryId) {
        if (code == null) {
            throw new RuntimeException("Mã không được để trống");
        }
        if (name == null) {
            throw new RuntimeException("Tên không được để trống");
        }
        if (quantity == null) {
            throw new RuntimeException("Số lượng không được để trống");
        }
        if (content == null) {
            throw new RuntimeException("Nội dung không được để trống");
        }
        if (categoryId == null) {
            throw new RuntimeException("Danh mục không được để trống");
        }
    }
}
