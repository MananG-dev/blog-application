package com.blogapp.utils;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "5";
    public static final String SORT_BY = "postId";
    public static final String SORT_DIR = "asc";

    public static final String[] PUBLIC_URLS = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v2/api-docs",
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/api/v1/auth/login",
            "/api/v1/auth/register"
    };
    public static final String Authorization = "Authorization";
    public static final String Bearer = "Bearer";
    public static final String BASE64_SECRET = "Qs7Z3fV7O0NxY6PfZn5j8TXyVH/1K5WTqAv9uHzrXH8=";

    public static final Integer ADMIN_USER = 501;
    public static final String ADMIN_USER_ROLE = "ROLE_ADMIN";
    public static final Integer NORMAL_USER = 502;
    public static final String NORMAL_USER_ROLE = "ROLE_NORMAL";


}
