package com.spring.jwt.jwtsecurity.handler;

import com.spring.jwt.jwtsecurity.handler.parent.SucceedResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

public class JwtAuthenticationSucceedHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationSucceedHandler.class);

    private SucceedResponseHandler succeedResponseHandler = new JwtAuthenticationSucceedResponseHandler();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOGGER.info("Delegating to response handler");
        clearAuthenticationAttributes(request);
        succeedResponseHandler.handle(response, request, authentication);
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the
     * session during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public SucceedResponseHandler getSucceedResponseHandler() {
        return succeedResponseHandler;
    }

    public void setSucceedResponseHandler(SucceedResponseHandler succeedResponseHandler) {
        this.succeedResponseHandler = succeedResponseHandler;
    }
}
