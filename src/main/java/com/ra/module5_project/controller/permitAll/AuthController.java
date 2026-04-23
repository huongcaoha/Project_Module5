package com.ra.module5_project.controller.permitAll;

import com.ra.module5_project.model.dto.token.RefreshTokenDTO;
import com.ra.module5_project.model.dto.user.request.UserLoginRequest;
import com.ra.module5_project.model.dto.user.request.UserRegisterRequest;
import com.ra.module5_project.model.dto.user.response.UserLoginResponse;

import com.ra.module5_project.security.principle.UserPrinciple;
import com.ra.module5_project.service.auth.AuthService;
import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api.myService.com/v1/auth")
public class AuthController {
    @Autowired
    private final AuthService authService ;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
//Đk
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
           boolean rs = authService.register(userRegisterRequest);
           if(rs){
               return new ResponseEntity<>("""
                            register success , please access the path to active account :
                                /api.myService.com/v1/auth/active-account/{email}/verify/{code}
                       """, HttpStatus.CREATED);
           }else {
               return new ResponseEntity<>("register error", HttpStatus.BAD_REQUEST);
           }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserLoginResponse> signIn(@Valid @RequestBody UserLoginRequest userLoginRequest,
                                                    HttpServletRequest request){
        return new ResponseEntity<>(authService.login(userLoginRequest,request),HttpStatus.OK);
    }

    @GetMapping("/active-account/{email}/verify/{code}")
    public ResponseEntity<?> activeAccount(@PathVariable String email ,@PathVariable long code){
       return authService.activeAccount(email,code);
    }

    @GetMapping("/getRefreshToken")
    public ResponseEntity<String> getRefreshToke(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO,
                                            HttpServletRequest request){
        return authService.getNewAccessToken(refreshTokenDTO,request);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserPrinciple userPrinciple){
        return authService.logout(userPrinciple.getUser());
    }
}
