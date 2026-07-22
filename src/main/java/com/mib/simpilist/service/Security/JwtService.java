package com.mib.simpilist.service.Security;

import com.mib.simpilist.dto.Auth.UserTokensDto;
import com.mib.simpilist.dto.Security.CurrentUserContext;
import com.mib.simpilist.exception.ForbiddenException;
import com.mib.simpilist.store.RefreshTokenStore;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.AuthFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final String issuer;
    private final RefreshTokenStore refreshTokenStore;

    public JwtService(
            @Value("${security.jwt.signing.secret}") String secret,
            @Value("${security.jwt.access-expiration}") long accessExpiration,
            @Value("${security.jwt.refresh-expiration}") long refreshExpiration,
            @Value("${security.jwt.issuer}") String issuer, RefreshTokenStore refreshTokenStore
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.issuer=issuer;
        this.refreshTokenStore = refreshTokenStore;
    }

    public String generateAccessToken(String email,String userId) {
        return Jwts.builder()
                .subject(email)
                .id(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .issuer(issuer)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String email,String userId) {

        String refreshToken= Jwts.builder()
                .subject(email)
                .id(userId)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .issuer(issuer)
                .signWith(secretKey)
                .compact();

        refreshTokenStore.save(refreshToken,
                userId,
                Duration.ofMillis(refreshExpiration));

        return refreshToken;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
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
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();
        Long id = Long.parseLong(claims.getId());
        CurrentUserContext currentUserContext=new CurrentUserContext(id,email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (Utilities.isNotNullOrEmpty(claims.get("roles"))) {
            authorities.addAll(Arrays.stream(claims.get("roles", String.class).split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList());
        }


        return new UsernamePasswordAuthenticationToken(currentUserContext, null, authorities);
    }

    public UserTokensDto refreshTokens(UserTokensDto userTokensDto){
        String refreshToken=userTokensDto.getRefreshToken();
        if(refreshTokenStore.exists(refreshToken)){
            removeRefreshToken(refreshToken);
        Claims claims=extractAllClaims(refreshToken);
            return AuthFactory.buildUserTokensDto(
                    generateAccessToken(claims.getSubject(),claims.getId()),
                    generateRefreshToken(claims.getSubject(),claims.getId()));
        }
        throw new ForbiddenException("Forbidden");
    }

    public void removeRefreshToken(String refreshToken){
        refreshTokenStore.delete(refreshToken);
    }
}