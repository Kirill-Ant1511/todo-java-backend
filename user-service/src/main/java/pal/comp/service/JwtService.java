package pal.comp.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


// Все методы для работы jwt токенами
@Service
public class JwtService {
    // Секретный ключ
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // Время жизни токена
    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;


    // Получение username из токена
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // получения данных с claims
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        // Получаем Claims
        final Claims claims = extractAllClaims(token);
        // Возвращаем переданную функцию передавая туда claims
        return claimsResolver.apply(claims);
    }

    // Генератор токена из userDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    // Генератор токена, только когда нужно передать какие-то свои данные в claims
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, expirationTime);
    }


    // Получение Время жизни токена
    public long getExpirationTime() {
        return expirationTime;
    }


    // Создание токена
    public String buildToken(Map<String, Object> claims, UserDetails userDetails, long expirationTime) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // Проверка токена на валидность, по времени жизни и тот ли это user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Проверка времени жизни токена
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Получение времени до которого может работать токен
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    // Получение всех claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Создание криптографического ключа для подписи и проверки JWT токенов
    // Зачем это нужно:
    // JWT токены должны быть подписаны для защиты от подделки
    // При создании токена (строка 71) и при его проверке (строка 97) используется этот же ключ
    // Это обеспечивает, что токен не был изменен после создания и действительно был выдан вашим сервисом
    private Key getSignKey() {
        // Декодирования секретного ключа в массив байтов
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Создание hmac ключ
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
