package com.spring.security.jwtsecurity.jwt;

import com.spring.security.jwtsecurity.configs.JwtTokenConfigProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * {@link JWTEngine} controls all features related to jwt.
 * @author Houston(Nayana)
 **/
public class JWTEngine implements TokenEngine {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Long ttl;
    private Boolean production;

    private JwtTokenConfigProperties configurationProperties;

    public JWTEngine(JwtTokenConfigProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
        this.ttl = configurationProperties.getTtl();
        this.production = configurationProperties.getProduction();
        postConstruct();
    }

    /**
     * Generate token
     * @param principleKey
     * @param credentialsKey
     * @param principal
     * @param credentials
     * @return
     */
    @Override
    public String generate(String principleKey, String credentialsKey, Object principal, Object credentials){
        Long millis=System.currentTimeMillis();
        Date expireTimeStamp=new java.util.Date(millis + ttl);
        return Jwts.builder()
                .setExpiration(expireTimeStamp)
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
    @Override
    public Boolean validate(String jwtToken){
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
     * @param attributeKey
     * @return
     */
    @Override
    public Object getAttribute(String jwtToken, String attributeKey){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().get(attributeKey);
    }

    @Override
    public void postConstruct() {

    }

}
