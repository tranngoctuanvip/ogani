package com.example.ogani_be.Security.UserPrincical;

import com.example.ogani_be.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> roles;

    public UserPrinciple(Long id, String password, String username, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }
    public static UserPrinciple biuld(User user){
        List<GrantedAuthority> authorities = user.getRoleSet().stream().map(role ->
                new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
        return new UserPrinciple(user.getId(),user.getPassword(),user.getUserName(),authorities);
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
