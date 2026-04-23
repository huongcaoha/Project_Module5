package com.ra.module5_project.security.jwt;

import com.ra.module5_project.model.entity.User;
import com.ra.module5_project.security.principle.UserPrinciple;
import com.ra.module5_project.service.auth.AuthServiceImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTProvider {
    @Value("${jwt_expired}")
    private long expired ;
    Logger logger = LoggerFactory.getLogger(JWTEntrypoint.class);
    @Value("${jwt_secret}")
    private String secret;
    public String generateAccessToken(User user){
        Date exp = new Date(new Date().getTime() + expired);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public String generateRefreshToken(User user, long code){
        LocalDateTime exp = LocalDateTime.now().plusDays(30);
        Date date = Date.from(exp.atZone(ZoneId.systemDefault()).toInstant());;
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(date)
                .claim("code",code)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public Boolean validateToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.get("code", String.class) == null;
        }catch (ExpressionException | SignatureException | ExpiredJwtException | MalformedJwtException ex){
            logger.error("Exception Authentication {}", ex.getMessage());
        }
        return false ;

    }

    public Boolean validateRefreshToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Long codeToken = claims.get("code", Long.class);
            return codeToken != null;
        }catch (ExpressionException | SignatureException | ExpiredJwtException | MalformedJwtException ex){
            logger.error("Exception Authentication {}", ex.getMessage());
            return false ;
        }

    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
