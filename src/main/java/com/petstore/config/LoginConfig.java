package com.petstore.config;

import com.petstore.controller.AdminInterceptor;
import com.petstore.controller.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class LoginConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册AdminInterceptor拦截器
        InterceptorRegistration adminRegistration = registry.addInterceptor(adminInterceptor);
        adminRegistration.addPathPatterns("/admin/**");//所有路径都被拦截
        adminRegistration.excludePathPatterns(//添加不拦截路径
                                          "你的登陆路径",            //登录
                                           "/**/*.html",            //html静态资源
                                           "/**/*.js",              //js静态资源
                                           "/**/*.css",             //css静态资源
                                            "/**/*.woff",
                                            "/**/*.ttf"
                                        );
        InterceptorRegistration userRegistration = registry.addInterceptor(userInterceptor);
        userRegistration.addPathPatterns("/index/**");//所有路径都被拦截
        userRegistration.excludePathPatterns(//添加不拦截路径
                                         "你的登陆路径",            //登录
                                         "/**/*.html",            //html静态资源
                                         "/**/*.js",              //js静态资源
                                         "/**/*.css",             //css静态资源
                                         "/**/*.woff",
                                         "/**/*.ttf"
                                        );
    }
}
