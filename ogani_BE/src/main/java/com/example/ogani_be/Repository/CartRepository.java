package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByIdAndDeleted(Long id, Integer deleted);
    @Query(value = "select p2.name, p2.image ,p2.price,c.quality,c.id from cart c \n" +
            "join product p2 on p2.id = c.product_id \n" +
            "where c.deleted =1 and c.status = 1 and c.create_by = :createBy",nativeQuery = true)
    List<Map<String,Object>> getUnpaidCart(@Param("createBy") Long createBy);
    @Query(value = "select count(c.id) as unpaid from cart c where c.deleted = 1 and c.status = 1 and c.create_by = :create_by",nativeQuery = true)
    List<Map<String,Object>> unpaid(@Param("create_by") Long create_by);
    
}