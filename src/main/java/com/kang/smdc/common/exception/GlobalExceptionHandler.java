package com.kang.smdc.common.exception;

import com.kang.smdc.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理自定义业务异常
   *
   * @param e 业务异常
   * @return 统一响应结果
   */
  @ExceptionHandler(BusinessException.class)
  public Result<Void> handleBusinessException(BusinessException e) {
    log.error("业务异常：{}", e.getMessage(), e);
    return Result.error(e.getCode(), e.getMessage());
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 统一响应结果
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Void> handleValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    String message = null;
    if (bindingResult.hasErrors()) {
      FieldError fieldError = bindingResult.getFieldError();
      if (fieldError != null) {
        message = fieldError.getDefaultMessage();
      }
    }
    log.error("参数校验异常：{}", message, e);
    return Result.error(message);
  }

  /**
   * 处理参数绑定异常
   *
   * @param e 参数绑定异常
   * @return 统一响应结果
   */
  @ExceptionHandler(BindException.class)
  public Result<Void> handleBindException(BindException e) {
    BindingResult bindingResult = e.getBindingResult();
    String message = null;
    if (bindingResult.hasErrors()) {
      FieldError fieldError = bindingResult.getFieldError();
      if (fieldError != null) {
        message = fieldError.getDefaultMessage();
      }
    }
    log.error("参数绑定异常：{}", message, e);
    return Result.error(message);
  }

  /**
   * 处理其他异常
   *
   * @param e 其他异常
   * @return 统一响应结果
   */
  @ExceptionHandler(Exception.class)
  public Result<Void> handleException(Exception e) {
    log.error("系统异常：{}", e.getMessage(), e);
    return Result.error("系统异常，请联系管理员");
  }
}