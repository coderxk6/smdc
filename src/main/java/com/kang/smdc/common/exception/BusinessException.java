package com.kang.smdc.common.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author kang
 * @since 2024-01-01
 */
@Getter
public class BusinessException extends RuntimeException {

  /**
   * 错误码
   */
  private final Integer code;

  /**
   * 错误信息
   */
  private final String message;

  /**
   * 构造函数
   *
   * @param message 错误信息
   */
  public BusinessException(String message) {
    this(0, message);
  }

  /**
   * 构造函数
   *
   * @param code    错误码
   * @param message 错误信息
   */
  public BusinessException(Integer code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  /**
   * 构造函数
   *
   * @param code    错误码
   * @param message 错误信息
   * @param cause   异常原因
   */
  public BusinessException(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.message = message;
  }
}