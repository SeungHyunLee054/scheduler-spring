package com.lsh.scheduler.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 암호화 util 클래스
 */
public class PasswordUtils {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
