package com.spring.jwt.jwtsecurity.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * @author Houston(Nayana)
 **/

public class JWTEngine {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generate token
     * @param principleKey
     * @param credentialsKey
     * @param principal
     * @param credentials
     * @return
     */
    public static String generate(String principleKey, String credentialsKey, Object principal, Object credentials){
        return Jwts.builder()
                .claim(principleKey, principal)
                .claim(credentialsKey, credentials)
                .signWith(key)
                .compact();
    }

    /**
     * Validate
     * @param jwtToken
     * @return
     */
    public static boolean validate(String jwtToken){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Fetch claims
     * @param jwtToken
     * @param attrubuteKey
     * @return
     */
    public static Object getAttribute(String jwtToken, String attrubuteKey){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().get(attrubuteKey);
    }

}
