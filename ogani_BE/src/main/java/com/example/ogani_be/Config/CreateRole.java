package com.example.ogani_be.Config;

import com.example.ogani_be.Entity.Role;
import com.example.ogani_be.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CreateRole implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(roleRepository.findByCode("ADMIN") == null){
            roleRepository.save(new Role("Quản trị viên","ADMIN"));
        }
        if(roleRepository.findByCode("USER") == null){
            roleRepository.save(new Role("Người dùng","USER"));
        }
        if(roleRepository.findByCode("STAFF") == null){
            roleRepository.save(new Role("Nhân viên","STAFF"));
        }
        if(roleRepository.findByCode("SHIPPER") == null){
            roleRepository.save(new Role("Người giao hàng","SHIPPER"));
        }
    }
}
