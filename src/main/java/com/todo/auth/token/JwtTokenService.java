package com.todo.auth.token;

import javax.crypto.SecretKey;

import com.todo.auth.dto.Credentials;
import com.todo.user.User;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService implements TokenService {
    private final SecretKey key = Keys.hmacShaKeyFor("qwerqwerqwerqwerqwerqwerqwerqwer".getBytes());

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Token createToken(User user, Credentials credentials) {
        TokenSubject tokenSubject = new TokenSubject(user.getId(), user.isActive());
        try {
            String subject = mapper.writeValueAsString(tokenSubject);
            String token = Jwts.builder().setSubject(subject).signWith(key).compact();
            return new Token("Bearer", token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        var token = authorizationHeader.split(" ")[1];
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jws<Claims> claims = parser.parseClaimsJws(token);
            var subject = claims.getBody().getSubject();
            var tokenSubject = mapper.readValue(subject, TokenSubject.class);
            User user = new User();
            user.setId(tokenSubject.id());
            user.setActive(tokenSubject.active());
            return user;
        } catch (JwtException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public record TokenSubject(long id, boolean active) {
    }
}