package com.spring.security.jwtsecurity.extractor;

import com.spring.security.jwtsecurity.CONSTANT;
import com.spring.security.jwtsecurity.exception.JWTTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Bearer token extractor from request header
 *
 * @author Houston(Nayana)
 **/
public class BearerTokenExtractor implements HeaderPropertyExtractor {

    private final Logger LOGGER = LoggerFactory.getLogger(BearerTokenExtractor.class);
    private final String HEADER_JWT_TOKEN_KEY = CONSTANT.HEADER_JWT_TOKEN_KEY;
    private final String TOKEN_START_WITH = CONSTANT.TOKEN_START_WITH;

    @Override
    public Object extract(HttpServletRequest httpServletRequest) {
        LOGGER.info("Extracting initiated");
        String bearerToken = httpServletRequest.getHeader(HEADER_JWT_TOKEN_KEY);

        if (bearerToken == null) {
            LOGGER.debug("Token not found in header");
            throw new JWTTokenException(CONSTANT.JWT_BEARER_TOKEN_VALIDATION_ERROR);
        }

        if (!bearerToken.startsWith(TOKEN_START_WITH)) {
            LOGGER.debug("Cannot extract Bearer token");
            throw new JWTTokenException(CONSTANT.JWT_BEARER_TOKEN_VALIDATION_ERROR);
        }

        LOGGER.debug("Token before " + bearerToken);
        bearerToken = bearerToken.substring(7);
        LOGGER.debug("Token before " + bearerToken);
        LOGGER.info("Extracting completed.");
        return bearerToken;
    }
}
