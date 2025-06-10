package com.kang.smdc.common.result;

import lombok.Data;

/**
 * 统一响应结果类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
public class Result<T> {
  /**
   * 状态码：1成功，0失败
   */
  private Integer code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 返回数据
   */
  private T data;

  /**
   * 私有构造方法，禁止直接创建
   */
  private Result() {
  }

  /**
   * 成功返回结果
   *
   * @param <T> 数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success() {
    return success(null);
  }

  /**
   * 成功返回结果
   *
   * @param data 返回数据
   * @param <T>  数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success(T data) {
    Result<T> result = new Result<>();
    result.setCode(1);
    result.setMsg("操作成功");
    result.setData(data);
    return result;
  }

  /**
   * 失败返回结果
   *
   * @param msg 错误信息
   * @param <T> 数据类型
   * @return 失败结果
   */
  public static <T> Result<T> error(String msg) {
    return error(0, msg);
  }

  /**
   * 失败返回结果
   *
   * @param code 错误码
   * @param msg  错误信息
   * @param <T>  数据类型
   * @return 失败结果
   */
  public static <T> Result<T> error(Integer code, String msg) {
    Result<T> result = new Result<>();
    result.setCode(code);
    result.setMsg(msg);
    return result;
  }
}