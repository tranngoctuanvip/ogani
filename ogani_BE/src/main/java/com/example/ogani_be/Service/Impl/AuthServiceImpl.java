package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Common.Utils.Utils;
import com.example.ogani_be.DTO.*;
import com.example.ogani_be.Entity.Role;
import com.example.ogani_be.Entity.User;
import com.example.ogani_be.Repository.RoleRepository;
import com.example.ogani_be.Repository.UserRepository;
import com.example.ogani_be.Security.UserPrincical.UserPrinciple;
import com.example.ogani_be.Security.jwt.JwtProvider;
import com.example.ogani_be.Service.AuthService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    private final Mapper mapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    @Override
    public User signup(Signup signup) {
        Utils.validateEmail(signup.getEmail());
        User user = new User();
        Optional<User> optionalUser = userRepository.findByUserNameAndDeleted(signup.getUserName(), Constant.NOTDELETE);
        if (optionalUser.isPresent()){
            throw new RuntimeException("Tên tài khoản đã tồn tại");
        }
        Optional<User> optional = userRepository.findByEmailAndDeleted(signup.getEmail(), Constant.NOTDELETE);
        if (optional.isPresent()){
            throw new RuntimeException("Email đã tồn tại");
        }
        user.setEmail(signup.getEmail());
        user.setDeleted(Constant.NOTDELETE);
        user.setStatus(Constant.STATUS);
        user.setCreateAt(LocalDateTime.now());
        user.setUserName(signup.getUserName());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        userRepository.save(user);
        if (signup.getRoles().size() == 0){
            userRepository.create(user.getId(), 6l);
        }
        else {
            for (String o : signup.getRoles()){
                Role role = roleRepository.findByCode(o);
                userRepository.create(user.getId(), role.getId());
            }
        }
        return user;
    }

    @Override
    public Signin signIn(Signin signin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signin.getUsername(),signin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        signin.setUsername(userPrinciple.getUsername());
        signin.setToken(token);
        signin.setPassword(userPrinciple.getPassword());
        signin.setType(signin.getType());
        signin.setRole(userPrinciple.getAuthorities());
        return signin;
    }

    @Override
    public void sendOTP(String email) {
        Utils.validateEmail(email);
        Optional<User> userOptional = userRepository.findByEmailAndDeleted(email,Constant.NOTDELETE);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Email không tồn tại");
        }
        String otp = generateOTP();
        User user = userOptional.get();
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        user.setOtpExpiryAt(expiryTime);
        userRepository.save(user);
        sendEmail(email,otp);
    }

    @Override
    public void resetPassword(String username, String OTP, String newPassword) {
        Optional<User> userOptional = userRepository.findByUserNameAndDeleted(username,Constant.NOTDELETE);
        if (userOptional.isEmpty()){
            throw new RuntimeException("Tài khoản không chính xác");
        }
        User user = userOptional.get();
        if (!user.getOtp().equals(OTP)) {
            throw new RuntimeException("Invalid OTP");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(user.getOtpExpiryAt())) {
            throw new RuntimeException("OTP has expired");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePassword chagePassword) {
        Optional<User> userOptional = userRepository.findByIdAndDeleted(mapper.getUserId(),Constant.NOTDELETE);
        User user = userOptional.get();
        if (passwordEncoder.matches(chagePassword.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(chagePassword.getNewPassword()));
            user.setUpdateAt(LocalDateTime.now());
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
    }

    @Override
    public List<UserDto> getAll(Pageable pageable) {
        List<UserDto> userDtoList = new ArrayList<>();
        var userList = userRepository.getAll(pageable);
        for (Map<String,Object> user : userList){
            BigInteger bigIntegerValue = (BigInteger) user.get("id");
            Long id = bigIntegerValue.longValue();
            UserDto userDto = new UserDto();
            userDto.setId(id);
            userDto.setStatus((Integer) user.get("status"));
            userDto.setEmail((String) user.get("email"));
            userDto.setUsername((String) user.get("user_name"));
            var roleId = userRepository.getRole(id);
            List<RoleDto> roleDtoList = new ArrayList<>();
            for (Map<String,Object> map :roleId){
               RoleDto roleDto = new RoleDto();
               roleDto.setRole((String) map.get("name_role"));
               roleDtoList.add(roleDto);
            }
            userDto.setRoleDtos(roleDtoList);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private String generateOTP() {
         int OTP_LENGTH = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @SneakyThrows
    private void sendEmail(String email,String OTP) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("faker2kvip@gmail.com", "hỗ trợ Email");
        helper.setTo(email);
        String subject = "Welcome to our website";
        String body = "<h1>Welcome!</h1>" +
                "Đây là mã OTP của bạn " + OTP +
                "<p>Thank you for joining our website.</p>"
                +"<br>Mã sẽ hết hạn sau 2 phút"
                +"<br>Đừng chia sẻ mã này cho bất kỳ ai";
        helper.setSubject(subject);
        helper.setText(body,true);
        javaMailSender.send(message);
    }
}
