package com.spring.security.jwtsecurity.extractor;

import com.spring.security.jwtsecurity.CONSTANT;

import javax.servlet.http.HttpServletRequest;

/**
 * Header token field extractor from header (example)
 * @author Houston(Nayana)
 **/
public class PlainTokenExtractor implements HeaderPropertyExtractor {

    private final String HEADER_JWT_TOKEN_KEY = CONSTANT.HEADER_JWT_TOKEN_KEY;

    @Override
    public Object extract(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(HEADER_JWT_TOKEN_KEY);
    }
}
