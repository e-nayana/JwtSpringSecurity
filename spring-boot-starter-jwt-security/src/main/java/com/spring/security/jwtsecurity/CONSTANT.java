package com.spring.security.jwtsecurity;

/**
 * @author Houston(Nayana)
 **/

public class CONSTANT {

    /**
     * Constant variables
     */
    public static final String EMPTY_STRING= "";
    public static final String MATCH_ALL_URL= "/**";
    public static final String HEADER_JWT_TOKEN_KEY = "Authorization";
    public static final String TOKEN_START_WITH = "Bearer ";
    public static final String AUTHENTICATING_URL = "/authenticate";
    public static final String AUTHENTICATING_METHOD = "POST";
    public static final String KEY_ALPHA = "username";
    public static final String KEY_BETA = "password";
    public static final String AUTH_SUCCESS_KEY_ALPHA = "username";
    public static final String AUTH_SUCCESS_KEY_ROLE = "role";
    public static final String AUTH_SUCCESS_KEY_PERMISSION = "permissions";



    /**
     * Constant Messages
     */
    public static final String AUTH_METHOD_NOT_SUPPORTED = "Authentication method not supported: ";
    public static final String JWT_TOKEN_VALIDATION_ERROR = "Jwt token is not valid.";
    public static final String JWT_BEARER_TOKEN_VALIDATION_ERROR = "Jwt bearer token not found.";
    public static final String APPLYED_VALUE_ERROR = "Value cant be null or empty";
    public static final String KEY_ALPHA_VALIDATION_ERROR = "Validation error of " + KEY_ALPHA;
    public static final String KEY_BETA_VALIDATION_ERROR = "Validation error of " + KEY_BETA;


}
