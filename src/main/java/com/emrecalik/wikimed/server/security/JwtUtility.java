package com.emrecalik.wikimed.server.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationInMs}")
    private String jwtExpirationInMs;

    CustomUserDetailsService userDetailsService;

    public JwtUtility(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return doGenerateToken(userPrincipal.getUsername());
    }

    public String doGenerateToken(String userName) {
        Date now = new Date();
        Instant expirationDate = Instant.ofEpochMilli(now.getTime() + Long.parseLong(jwtExpirationInMs));
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(Date.from(expirationDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String jwtToken, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            request.setAttribute("expired", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("Illegal JWT claim");
        }
        return false;
    }

    public String getUserNameFromToken(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
