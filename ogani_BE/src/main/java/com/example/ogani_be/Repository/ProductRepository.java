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
    @Query(value = " select * from product p2 where p2.deleted =1 order by p2.id desc limit 6",nativeQuery = true)
    List<Product> getNewProduct();
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
    @Query(value = "select p.id, p.image ,p.name ,p.price \n" +
            "               from product p join product_category pc on pc.product_id = p.id \n" +
            "             where p.deleted = 1 \n" +
            "             and (pc.category_id = :category_id or :category_id is null)\n" +
            "             and (lower(p.name) like concat('%', lower(:name) ,'%') or :name is null)",nativeQuery = true)
    List<Map<String,Object>> getProduct(@Param("category_id") Integer category_id, @Param("name") String name, Pageable pageable);
    @Query(value = "select p2.id, p2.image ,p2.name ,p2.price ,p2.quantity, pd.content, pc.category_id from product p2 \n" +
            "              join product_category pc on pc.product_id = p2.id\n" +
            "              join product_detail pd on pd.product_id = p2.id where p2.id = :id",nativeQuery = true)
    List<Map<String,Object>> findByIdProduct(@Param("id") Long id);
}