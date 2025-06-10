package com.kang.smdc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j配置类
 *
 * @author kang
 * @since 2024-01-01
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

  /**
   * 配置API文档
   *
   * @return Docket
   */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        // 扫描controller包路径
        .apis(RequestHandlerSelectors.basePackage("com.kang.smdc.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  /**
   * API文档信息
   *
   * @return ApiInfo
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("扫码点餐系统接口文档")
        .description("扫码点餐系统接口文档")
        .contact(new Contact("kang", "", ""))
        .version("1.0.0")
        .build();
  }
}