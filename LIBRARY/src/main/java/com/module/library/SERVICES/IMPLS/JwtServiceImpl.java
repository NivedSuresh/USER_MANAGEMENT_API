package com.module.library.SERVICES.IMPLS;

import com.module.library.SERVICES.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    public String generateJwt(Authentication authentication){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 2139999999))
                .claim("email", authentication.getName())
                .claim("authority", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority))
                .signWith(key).compact();
    }

    public boolean validateJwt(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String[] getDataFromJwt(String jwt){
        Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt.substring(7))
                        .getBody();

        return  new String[]{String.valueOf(claims.get("email")), String.valueOf(claims.get("authority"))};
    }

}
