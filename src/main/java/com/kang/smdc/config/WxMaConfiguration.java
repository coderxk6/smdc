package com.kang.smdc.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaConfiguration {

  /**
   * 设置微信小程序的appid
   */
  private String appid;

  /**
   * 设置微信小程序的Secret
   */
  private String secret;

  /**
   * 设置微信小程序消息服务器配置的token
   */
  private String token;

  /**
   * 设置微信小程序消息服务器配置的EncodingAESKey
   */
  private String aesKey;

  /**
   * 消息格式，XML或者JSON
   */
  private String msgDataFormat;

  @Bean
  public WxMaService wxMaService() {
    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
    config.setAppid(appid);
    config.setSecret(secret);
    config.setToken(token);
    config.setAesKey(aesKey);
    config.setMsgDataFormat(msgDataFormat);

    WxMaService service = new WxMaServiceImpl();
    service.setWxMaConfig(config);
    return service;
  }
}