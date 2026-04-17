package com.ra.module5_project.service.auth;

import com.ra.module5_project.model.dto.token.RefreshTokenDTO;
import com.ra.module5_project.model.dto.user.request.UserLoginRequest;
import com.ra.module5_project.model.dto.user.request.UserRegisterRequest;
import com.ra.module5_project.model.dto.user.request.UserUpdatePasswordDto;
import com.ra.module5_project.model.dto.user.response.UserLoginResponse;
import com.ra.module5_project.model.entity.Role;
import com.ra.module5_project.model.entity.User;
import com.ra.module5_project.repository.RoleRepository;
import com.ra.module5_project.repository.UserRepository;
import com.ra.module5_project.security.SecurityConfig;
import com.ra.module5_project.security.jwt.JWTProvider;
import com.ra.module5_project.security.principle.UserPrinciple;
import com.ra.module5_project.service.sendMail.ActiveAccount;
import com.ra.module5_project.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private ActiveAccount activeAccount;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SecurityConfig securityConfig ;
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest ,HttpServletRequest request) {
        Authentication authentication;
        authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),userLoginRequest.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();
        String token = jwtProvider.generateAccessToken(user);
        user.setStatusLogin(true);
        userRepository.save(user);
        long code = randomCode();
        String ipAddress = getClientIp(request);
        return UserLoginResponse.builder()
                .username(userPrinciple.getUsername())
                .accessToken(token)
                .refreshToken(jwtProvider.generateRefreshToken(user,ipAddress,code))
                .code(code)
                .typeToken("Bearer")
                .roles(userPrinciple.getUser().getRoles().stream().map(Role::getRoleName).collect(Collectors.joining(",")))
                .build();
    }

    @Override
    public User changePassword(UserUpdatePasswordDto userUpdatePasswordDto) {
//        Authentication authentication;
//        authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userUpdatePasswordDto.getUsername(),userUpdatePasswordDto.getPassword()));
//        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
//        return userPrinciple.getUser();
        return new User();
    }

    @Override
    public boolean register(UserRegisterRequest userRegisterRequest) {
        long code = randomCode();
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByRoleName("USER"));
        User user = User.builder()
                .username(userRegisterRequest.getUsername())
                .password(securityConfig.passwordEncoder().encode(userRegisterRequest.getPassword()))
                .email(userRegisterRequest.getEmail())
                .phone(userRegisterRequest.getPhone())
                .address(userRegisterRequest.getAddress())
                .roles(roles)
                .status(false)
                .fullName(userRegisterRequest.getFullName())
                .created_at(new Date())
                .activeCode(code)
                .build();

        try {
            activeAccount.sendCode(userRegisterRequest.getEmail(),"Active Account",code);
             userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long randomCode() {
        Random random = new Random();
        return 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L);
    }

    @Override
    public ResponseEntity<String> activeAccount(String email , long code) {
        User user = userRepository.getUserByEmail(email);
        if(user != null ){
            if(user.getActiveCode() == code){
                long newCode= randomCode();
                user.setActiveCode(newCode);
                user.setStatus(true);
                userRepository.save(user);
                return new ResponseEntity<>("Active account success", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Active account error",HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("Active account error",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String getNewAccessToken(RefreshTokenDTO refreshTokenDTO , HttpServletRequest request) {
        String ipAddress = getClientIp(request);
        boolean checkValidateToken = jwtProvider.validateRefreshToken(refreshTokenDTO.getRefreshToken(),ipAddress,refreshTokenDTO.getCode());
        if(checkValidateToken){
            String username = jwtProvider.getUsernameFromToken(refreshTokenDTO.getRefreshToken());
            User user = userRepository.getUserByUsername(username);
            return jwtProvider.generateAccessToken(user);
        }else {
            return "Token không hợp lệ" ;
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        // Danh sách các header phổ biến mà Proxy dùng để chuyển tiếp IP thực
        String[] headerNames = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "VIA",
                "REMOTE_ADDR"
        };

        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // X-Forwarded-For có thể chứa danh sách IP phân tách bởi dấu phẩy (IP1, IP2, IP3)
                // IP đầu tiên luôn là IP thực của Client
                return ip.split(",")[0];
            }
        }

        // Nếu không có header nào, lấy địa chỉ trực tiếp (dùng khi chạy localhost)
        return request.getRemoteAddr();
    }
}
