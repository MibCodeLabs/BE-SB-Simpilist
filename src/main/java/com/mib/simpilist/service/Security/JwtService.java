package com.mib.simpilist.service.Security;

import com.mib.simpilist.dto.Security.CurrentUserContext;
import com.mib.simpilist.model.User;
import com.mib.simpilist.utililty.Utilities;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final Key secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final String issuer;

    public JwtService(
            @Value("${security.jwt.signing.secret}") String secret,
            @Value("${security.jwt.access-expiration}") long accessExpiration,
            @Value("${security.jwt.refresh-expiration}") long refreshExpiration,
            @Value("${security.jwt.issuer}") String issuer
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.issuer=issuer;
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .setIssuer(issuer)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .setIssuer(issuer)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();
        String id = claims.getId();
        CurrentUserContext currentUserContext=new CurrentUserContext(Long.getLong(id),email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (Utilities.isNotNullOrEmpty(claims.get("roles"))) {
            authorities.addAll(Arrays.stream(claims.get("roles", String.class).split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList());
        }


        return new UsernamePasswordAuthenticationToken(currentUserContext, null, authorities);
    }
}