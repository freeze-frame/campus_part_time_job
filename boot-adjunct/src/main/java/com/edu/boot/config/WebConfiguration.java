//package com.edu.boot.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//
///**
// * 解决跨域问题
// */
//@Configuration
//public class WebConfiguration implements WebMvcConfigurer {
//
//    private TokenInterceptor tokenInterceptor;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    //构造方法
//    public WebConfiguration(TokenInterceptor tokenInterceptor){
//        this.tokenInterceptor = tokenInterceptor;
//    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") //允许跨域访问的路径
//                .allowedOriginPatterns("*") //允许跨域访问的源
//                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS") //允许请求方法
//                .allowCredentials(true) //是否发送cookie
//                .maxAge(3600) //预检间隔时间
//                .allowedHeaders("*");//允许头部设置
//    }
//
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
//        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
//        configurer.setDefaultTimeout(30000);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        List<String> excludePath = new ArrayList<>();
//        // 排除拦截
//        excludePath.add("/login");  //登录
//        excludePath.add("/register");  //注册
//        excludePath.add("/webjars/**");
//        excludePath.add("/images/**");
//        excludePath.add("/static/**");  //静态资源
//        excludePath.add("/assets/**");  //静态资源
//        excludePath.add("/stu/notes/download/**");  //静态资源
//        logger.info("====通过登录拦截器====");
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludePath);
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
//


package com.edu.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Web配置类，解决跨域问题并配置拦截器
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造方法，注入Token拦截器
     *
     * @param tokenInterceptor Token拦截器实例
     */
    public WebConfiguration(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置跨域请求
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 配置异步支持
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)))
                .setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 排除拦截的路径列表
        List<String> excludePath = getExcludePaths();
        logger.info("====通过登录拦截器====");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * 获取排除拦截的路径列表
     *
     * @return 排除拦截的路径列表
     */
    private List<String> getExcludePaths() {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/login");
        excludePath.add("/register");
        excludePath.add("/webjars/**");
        excludePath.add("/images/**");
        excludePath.add("/static/**");
        excludePath.add("/assets/**");
        excludePath.add("/stu/notes/download/**");
        return excludePath;
    }
}