package com.ra.module5_project.service.refreshToken;

import com.ra.module5_project.model.entity.RefreshToken;
import com.ra.module5_project.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }
}
