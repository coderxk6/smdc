package com.kang.smdc.common.util;

import com.kang.smdc.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 工具类
 * (此为简化版，生产环境请使用更完善的JWT库和安全实践)
 * 
 * @author kang
 */
@Component
public class JwtUtil {

  private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24; // 24小时过期
  // private static final String SECRET_STRING = "c21kY19zZWNyZXQ="; //
  // Base64编码后的密钥，不再需要
  // private static final Key SECRET_KEY =
  // Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING)); // 不再通过解码短字符串生成
  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 直接生成一个安全的HS256密钥

  /**
   * 生成JWT Token
   * 
   * @param userId 用户ID
   * @return Token字符串
   */
  public static String generateToken(Long userId) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + EXPIRE_TIME);

    return Jwts.builder()
        .setHeaderParam("type", "JWT")
        .setIssuedAt(now) // 签发时间
        .setExpiration(expiration) // 过期时间
        .claim("userId", userId) // 用户ID
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 使用Key对象签名
        .compact();
  }

  /**
   * 从Token中获取用户ID
   * 
   * @param token Token字符串
   * @return 用户ID
   */
  public static Long getUserId(String token) {
    try {
      Claims claims = Jwts.parserBuilder() // 修改：使用parserBuilder()
          .setSigningKey(SECRET_KEY) // 使用Key对象设置签名密钥
          .build() // 构建parser
          .parseClaimsJws(token)
          .getBody();
      return claims.get("userId", Long.class);
    } catch (Exception e) {
      throw new BusinessException("token无效或已过期");
    }
  }
}