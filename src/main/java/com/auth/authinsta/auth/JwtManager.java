package com.auth.authinsta.auth;

import com.auth.authinsta.auth.model.Role;
import com.auth.authinsta.auth.model.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtManager {
    private String secretKey = "nEtQrbnutF3v1u9MYRQ4nw4981uBCWoI82O4Andb7BE";

    /**
     * For every authenticated user :
     * { id: 33993, role: "ADMIN" | "STUDENT" } will be set in the token
     */
    public String generateToken(Integer userId, Role userRole){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put( "role" , String.valueOf(userRole) );

        return
                Jwts.builder()
                    .setIssuedAt(new Date())
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
    }

    public boolean validateToken(String token) {
        try {
            Map<String, Object> claims = Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(token)
                   .getBody() ;
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public UserInfo getUserInfo(String token){
        try {
            Map<String, Object> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new UserInfo(
                    Integer.parseInt(claims.get("id").toString()),
                    Role.valueOf(claims.get("role").toString())
            );
        }
        catch(Exception e){
            return null;
        }
    }
}
