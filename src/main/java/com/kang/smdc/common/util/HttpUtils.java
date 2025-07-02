package com.kang.smdc.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
//import java.util.StringBuilder;

/**
 * HTTP 工具类
 *
 * @author kang
 * @since 2024-01-01
 */
public class HttpUtils {

  /**
   * 发送GET请求
   *
   * @param urlStr 请求地址
   * @param params 请求参数
   * @return 响应字符串
   */
  public static String doGet(String urlStr, Map<String, String> params) {
    StringBuilder result = new StringBuilder();
    HttpURLConnection connection = null;
    BufferedReader in = null;
    try {
      // 构建URL，包含参数
      String paramStr = buildParams(params);
      URL url = new URL(urlStr + (paramStr.isEmpty() ? "" : "?" + paramStr));

      // 打开连接
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      // 读取响应
      in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭连接和流
      if (connection != null) {
        connection.disconnect();
      }
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result.toString();
  }

  private static String buildParams(Map<String, String> params) throws Exception {
    StringBuilder paramBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (paramBuilder.length() > 0) {
        paramBuilder.append("&");
      }
      paramBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
          .append("=")
          .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
    }
    return paramBuilder.toString();
  }
}