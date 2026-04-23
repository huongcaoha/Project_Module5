package com.ra.module5_project.service.auth;


import com.ra.module5_project.model.dto.token.RefreshTokenDTO;
import com.ra.module5_project.model.dto.user.request.UserLoginRequest;
import com.ra.module5_project.model.dto.user.request.UserRegisterRequest;
import com.ra.module5_project.model.dto.user.request.UserUpdatePasswordDto;
import com.ra.module5_project.model.dto.user.response.UserLoginResponse;
import com.ra.module5_project.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletRequest request);
     User changePassword(UserUpdatePasswordDto userUpdatePasswordDto) ;
     boolean register(UserRegisterRequest userRegisterRequest);
     ResponseEntity<String> activeAccount(String email , long code);
     ResponseEntity<String> getNewAccessToken(RefreshTokenDTO refreshTokenDTO, HttpServletRequest request);
     ResponseEntity<String> logout(User user);
}

