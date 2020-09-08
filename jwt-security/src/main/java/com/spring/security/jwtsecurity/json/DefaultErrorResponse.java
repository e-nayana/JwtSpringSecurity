package com.spring.security.jwtsecurity.json;

/**
 * @author Houston(Nayana)
 **/

/**
 * This is the default error body structure which has followed the RFC 7807 standard.
 * Find the example error body below
 * {
 *             "type": "AuthenticationException",
 *             "title": "Incorrect username or password.",
 *             "status": 401,
 *             "detail": "Authentication failed due to incorrect username or password.",
 *             "instance": "/authenticate"
 * }
 */
public class DefaultErrorResponse {

    String type;
    String title;
    String status;
    String detail;
    String instance;

    public DefaultErrorResponse(String type, String title, String status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
