package com.spring.security.jwtsecurity.configs;

/**
 * This is the property holder for jwt authentication.
 * @author Houston(Nayana)
 **/
public class JwtTokenConfigProperties {
    private Long ttl = 60000L;
    private Boolean production = true;

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Boolean getProduction() {
        return production;
    }

    public void setProduction(Boolean production) {
        this.production = production;
    }
}
