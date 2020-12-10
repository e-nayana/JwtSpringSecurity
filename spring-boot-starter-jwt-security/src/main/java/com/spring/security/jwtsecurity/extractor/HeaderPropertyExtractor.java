package com.spring.security.jwtsecurity.extractor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Houston(Nayana)
 **/
public interface HeaderPropertyExtractor {
    Object extract(HttpServletRequest httpServletRequest);
}
