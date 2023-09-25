package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Role;
import com.example.ogani_be.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findByDeleted(Integer deleted);
    Optional<User> findByIdAndDeleted(Long id, Integer deleted);
    Optional<User> findByEmailAndDeleted(String email, Integer deleted);
    Optional<User> findByUserNameAndDeleted(String userName, Integer deleted);
    @Modifying
    @Transactional
    @Query(value = "insert into user_role (user_id,role_id) values (:user_id,:role_id)",nativeQuery = true)
    void create(@Param("user_id") Long user_id, @Param("role_id") Long role_id);

    @Query(value = " select u.id ,u.user_name,u.name ,u.status from `user` u \n" +
            "                           where u.deleted = 1 and (u.name = :name or :name is null)",nativeQuery = true)
    List<Map<String, Object>> getAll(@Param("name") String name, Pageable pageable);
    @Query(value = "select r.name_role from `role` r \n" +
            "               join user_role ur on ur.role_id  = r.id \n" +
            "               join `user` u on u.id = ur.user_id \n" +
            "               where u.id = :id ",nativeQuery = true)
    List<Map<String,Object>> getRole(@Param("id") Long id);

    @Override
    Optional<User> findById(Long aLong);
}