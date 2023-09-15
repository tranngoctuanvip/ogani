package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDeleted(Integer deleted);
    Optional<Product> findByIdAndDeleted(Long id, Integer deleted);
    @Transactional
    @Modifying
    @Query(value = "insert into product_category(category_id,product_id) values(:categoryId,:productId)",nativeQuery = true)
    void create(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
    @Modifying
    @Transactional
    @Query(value = " update product_category set (category_id =:category_id) where product_id =:product_id ",nativeQuery = true)
    void update(@Param("category_id") Long category_id, @Param("product_id") Long product_id);
    @Query(value = "select p.image ,p.name, p.price from product p where p.deleted = 1" +
            " order by p.id desc limit 3",nativeQuery = true)
    List<Map<String,Object>> lastestProduct();
    @Query(value = " select p.id, p.image ,p.name ,p.price from product p \n" +
            "    where p.deleted = 1",nativeQuery = true)
    List<Map<String,Object>> getProduct(Pageable pageable);
}