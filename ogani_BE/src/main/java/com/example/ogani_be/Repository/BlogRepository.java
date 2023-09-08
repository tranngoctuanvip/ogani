package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findByIdAndDeleted(Long id, Integer deleted);
    @Query(value = "select b.image ,b.content ,b.create_at ,b.title from blog b where b.deleted = 1 " +
            "order by b.id desc limit 3",nativeQuery = true)
    List<Map<String, Object>> selectTop3();

    @Query(value = "select b2.code, b2.content ,b2.image ,b2.status ,b2.title from blog b2 where b2.deleted = 1"
            ,nativeQuery = true)
    List<Map<String,Object>> getAll(Pageable pageable);


}