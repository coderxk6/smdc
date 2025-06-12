package com.kang.smdc.config;

import com.kang.smdc.interceptor.AdminAuthInterceptor;
import com.kang.smdc.interceptor.MiniAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 *
 * @author kang
 * @since 2024-01-01
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private AdminAuthInterceptor adminAuthInterceptor;
  @Autowired
  @Lazy
  private MiniAuthInterceptor miniAuthInterceptor;

  @Value("${smdc.upload.path}")
  private String uploadPath;

  /**
   * 配置跨域
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }

  /**
   * 配置拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 管理端接口拦截器
    registry.addInterceptor(adminAuthInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin/employee/login");

    // 小程序端接口拦截器
    registry.addInterceptor(miniAuthInterceptor)
        .addPathPatterns("/mini/**")
        .excludePathPatterns(
            "/mini/user/login",
            "/mini/user/account/login",
            "/mini/shop/info");
  }

  /**
   * 设置静态资源映射
   * 
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    log.info("开始进行静态资源映射...");
    registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadPath);
  }
}