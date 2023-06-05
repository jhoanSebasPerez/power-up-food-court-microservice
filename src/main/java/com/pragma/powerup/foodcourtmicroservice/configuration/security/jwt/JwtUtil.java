package com.pragma.powerup.foodcourtmicroservice.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.pragma.powerup.foodcourtmicroservice.configuration.security.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.authority.*;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public static Map<String, Object> getClaims(String token) throws ParseException {
        JWT jwt = JWTParser.parse(token);
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        return claims.getClaims();
    }

    public static List<SimpleGrantedAuthority> convertToAuthorities(String token) {
        List<String> roles = null;
        try {
            roles = (ArrayList<String>) JwtUtil.getClaims(token).get("roles");
        } catch (ParseException e) {
            throw new TokenException();
        }
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public static String getDniFromToken(String token) {
        Map<String, Object> claims = null;
        try {
            claims = getClaims(token.substring(7));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        assert claims != null;
        return (String) claims.get("dni");
    }
}
