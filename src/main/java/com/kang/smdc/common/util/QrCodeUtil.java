package com.kang.smdc.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 二维码工具类
 * 实际项目中可以使用zxing等库生成真实的二维码图片
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
public class QrCodeUtil {

  /**
   * 模拟生成二维码路径
   * 实际应该是生成图片并返回图片存储路径
   *
   * @param content 二维码内容
   * @return 二维码图片存储路径（模拟）
   */
  public static String generateQrCode(String content) {
    log.info("模拟生成二维码，内容为：{}", content);
    // 实际项目中，这里会调用二维码生成库，生成图片并存储，然后返回图片访问路径
    // 例如：String filePath = "/path/to/qrcode/" + System.currentTimeMillis() + ".png";
    // QrCodeWriter.encode(content, filePath);
    return "/images/qrcodes/" + content.replaceAll("\\W+", "") + ".png";
  }
} 