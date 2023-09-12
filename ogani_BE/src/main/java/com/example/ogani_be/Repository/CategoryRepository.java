package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndDeleted(Long id, Integer deleted);

    @Query(value = "SELECT c.id,c.code ,c.name ,c.status \n" +
            "   FROM category c\n" +
            "   WHERE c.deleted = 1\n" +
            "   AND (\n" +
            "   lower(c.code) LIKE CONCAT('%',lower(:code), '%')\n" +
            "   OR lower(c.name) LIKE CONCAT('%',lower(:name), '%')\n" +
            "   OR :code IS null and :name IS NULL)",nativeQuery = true)
    List<Map<String,Object>> getByNameAndCode(@Param("code") String code, @Param("name") String name);
}