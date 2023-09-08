package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Optional<ProductDetail> findByProductIdAndDeleted(Long id, Integer notdelete);
}