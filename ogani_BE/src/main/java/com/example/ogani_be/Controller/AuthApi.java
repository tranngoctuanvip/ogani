package com.example.ogani_be.Controller;

import com.example.ogani_be.Common.Mapper.PageMapper;
import com.example.ogani_be.Common.Response.ResponseData;
import com.example.ogani_be.Common.Response.ResponseDataTotal;
import com.example.ogani_be.Common.Response.ResponseError;
import com.example.ogani_be.DTO.ChangePassword;
import com.example.ogani_be.DTO.Signin;
import com.example.ogani_be.DTO.Signup;
import com.example.ogani_be.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.ogani_be.Common.Response.ResponseDataStatus.ERROR;
import static com.example.ogani_be.Common.Response.ResponseDataStatus.SUCCESS;

@RestController
@RequestMapping("auth")
public class AuthApi {
    @Autowired
    private AuthService authService;
    @Autowired
    private PageMapper pageMapper;
    @PostMapping("signin")
    private ResponseEntity<?> signin(@RequestBody Signin signin){
        try{
            var login = authService.signIn(signin);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Login Successfull").data(login).build(), HttpStatus.OK);
        } catch (Exception e) {
        }            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                .message("Tài khoản hoặc mật khẩu không chính xác").build(),HttpStatus.BAD_REQUEST);
    }
    @PostMapping("signup")
    private ResponseEntity<?> signup(@RequestBody Signup signup){
        try{
            var register = authService.signup(signup);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Register Successfull").data(register).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("logout")
    public ResponseEntity<?> logout(){
        try {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Đăng xuất thành công").build(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseData.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("sendEmail")
    private ResponseEntity<?> sendEmail(@Param("email") String email){
        try{
            authService.sendOTP(email);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Send Email Successfull").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("resetPassword")
    private ResponseEntity<?> resetPassword(@Param("username") String username,@Param("OTP") String OTP,
                                            @Param("newPassword") String newPassword){
        try{
            authService.resetPassword(username,OTP,newPassword);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Reset Password Successfull").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("changePassword")
    private ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        try{
            authService.changePassword(changePassword);
            return new ResponseEntity<>(ResponseData.builder().status(SUCCESS.name())
                    .message("Change Password Successfull").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getUser")
    private ResponseEntity<?> getUser(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "5") Integer size,
                                      @RequestParam(defaultValue = "desc") String sortType,
                                      @RequestParam(defaultValue = "id") String sortBy){
        try{
            var pageable = pageMapper.pageable(page,size,sortType,sortBy);
            var getAll =authService.getAll(pageable);
            var pageUser = new PageImpl<>(getAll);
            var data = pageUser.getContent();
            var total = pageUser.getTotalElements();
            return new ResponseEntity<>(ResponseDataTotal.builder().status(SUCCESS.name())
                    .message("Get All Successfull").data(data).total(total).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseError.builder().status(ERROR.name())
                    .message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }
}
