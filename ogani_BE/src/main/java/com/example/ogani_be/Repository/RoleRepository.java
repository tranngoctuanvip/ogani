package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);

    @Override
    Optional<Role> findById(Long aLong);

//    Role findByRole(String admin);
}