package com.example.ogani_be.Security.UserPrincical;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Entity.User;
import com.example.ogani_be.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Userdetail implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameAndDeleted(username, Constant.NOTDELETE).orElseThrow(() ->
                new UsernameNotFoundException("Username not found" +username));
        return UserPrinciple.biuld(user);
    }
}
