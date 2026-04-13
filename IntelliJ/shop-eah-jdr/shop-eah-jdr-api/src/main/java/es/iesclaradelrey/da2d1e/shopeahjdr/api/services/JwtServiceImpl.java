package es.iesclaradelrey.da2d1e.shopeahjdr.api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.access-token.expiration}")
    long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    long refreshTokenExpiration;

    @Override
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .signWith(getKey())
                .subject(username)
                .claim("type", "access")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .signWith(getKey())
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    @Override
    public boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        Claims claims = getClaims(token);
        String user = claims.getSubject();
        String type = claims.get("type", String.class);
        return user.equals(username)
                && "access".equals(type)
                && !isTokenExpired(token);
    }

//    @Override
//    public boolean isRefreshTokenValid(String token, String username) {
//        Claims claims = getClaims(token);
//        String user = claims.getSubject();
//        String type = claims.get("type", String.class);
//
//        return user.equals(username)
//                && "refresh".equals(type)
//                && !isTokenExpired(token);
//    }

    private SecretKey getKey() {
        byte[] secretBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
