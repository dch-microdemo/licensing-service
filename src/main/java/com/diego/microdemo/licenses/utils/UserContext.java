package com.diego.microdemo.licenses.utils;

import org.springframework.stereotype.Component;


@Component
public class UserContext {
    public static final String AUTH_TOKEN     = "Authorization";

    private static final ThreadLocal<String> authToken= new ThreadLocal<String>();

    public static String getAuthToken() { return authToken.get(); }
    public static void setAuthToken(String aToken) {authToken.set(aToken);}


}