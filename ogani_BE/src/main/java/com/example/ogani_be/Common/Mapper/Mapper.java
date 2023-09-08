package com.example.ogani_be.Common.Mapper;

import com.example.ogani_be.Security.UserPrincical.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Long getUserId(){
        Long userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
           userId = userPrinciple.getId();
        }
        return userId;
    }
}
