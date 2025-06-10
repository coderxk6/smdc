package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.User;

/**
 * 用户Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface UserService extends IService<User> {

  /**
   * 微信登录
   *
   * @param code 微信登录code
   * @return 用户信息
   */
  User loginByWechat(String code);

  /**
   * 账号密码登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 用户信息
   */
  User loginByAccount(String username, String password);

  /**
   * 获取当前登录用户信息
   *
   * @param userId 用户ID
   * @return 用户信息
   */
  User getCurrentUser(Long userId);

  /**
   * 更新用户信息
   *
   * @param user 用户信息
   * @return 更新后的用户信息
   */
  User updateUser(User user);
}