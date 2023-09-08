package com.example.ogani_be.Service;

import com.example.ogani_be.DTO.ChangePassword;
import com.example.ogani_be.DTO.Signin;
import com.example.ogani_be.DTO.Signup;
import com.example.ogani_be.DTO.UserDto;
import com.example.ogani_be.Entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AuthService {
    User signup(Signup signup);
    Signin signIn(Signin signin);
    void sendOTP(String email);
    void resetPassword(String username, String OTP, String newpassword);
    void changePassword(ChangePassword chagePassword);
    List<UserDto> getAll(Pageable pageable);
}
