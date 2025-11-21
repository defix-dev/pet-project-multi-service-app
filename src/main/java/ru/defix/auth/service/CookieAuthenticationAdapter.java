package ru.defix.auth.service;

import jakarta.servlet.http.Cookie;

public class CookieAuthenticationAdapter {
    private static final String AUTH_COOKIE_NAME = "token";

    public static Cookie adaptLoginCookie(String value) {
        Cookie c = new Cookie(AUTH_COOKIE_NAME, value);
        c.setHttpOnly(true);
        c.setPath("/");
        return c;
    }

    public static Cookie adaptLogoutCookie() {
        Cookie c = new Cookie(AUTH_COOKIE_NAME, "");
        c.setMaxAge(0);
        c.setPath("/");
        return c;
    }
}
