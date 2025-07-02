package com.kang.smdc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.common.exception.BusinessException;
import com.kang.smdc.common.util.HttpUtils;
import com.kang.smdc.common.util.JwtUtil;
import com.kang.smdc.entity.User;
import com.kang.smdc.mapper.UserMapper;
import com.kang.smdc.service.UserService;
import com.kang.smdc.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户Service实现类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Value("${wx.miniapp.appid}")
  private String appid;
  @Value("${wx.miniapp.secret}")
  private String secret;

  /**
   * 微信登录
   *
   * @param code 微信登录code
   * @return 用户信息和token
   */
  @Override
  public LoginVO loginByWechat(String code) {
    // 1. 调用微信接口服务，获取用户的 openid 和 session_key
    String url = "https://api.weixin.qq.com/sns/jscode2session";
    Map<String, String> params = new HashMap<>();
    params.put("appid", appid);
    params.put("secret", secret);
    params.put("js_code", code);
    params.put("grant_type", "authorization_code");

    String response = HttpUtils.doGet(url, params);
    JSONObject jsonObject = JSON.parseObject(response);
    String openid = jsonObject.getString("openid");
    String sessionKey = jsonObject.getString("session_key");

    // 2. 判断 openid 是否为空，如果为空表示登录失败
    if (openid == null) {
      throw new BusinessException("微信登录失败");
    }

    // 3. 根据 openid 查询用户是否存在
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getOpenId, openid);
    User user = getOne(queryWrapper);

    // 4. 如果用户不存在，则注册新用户
    if (user == null) {
      user = new User();
      user.setOpenId(openid);
      user.setSessionKey(sessionKey);
      user.setNickName("微信用户");
      user.setAvatarUrl("/images/default-avatar.png"); // 默认头像
      user.setCreateTime(LocalDateTime.now());
      user.setUpdateTime(LocalDateTime.now());
      save(user);
    } else {
      // 5. 更新 session_key 和最后登录时间
      user.setSessionKey(sessionKey);
      user.setLastLoginTime(LocalDateTime.now());
      updateById(user);
    }

    // 6. 生成JWT token
    String token = JwtUtil.generateToken(user.getId());

    // 7. 返回用户信息和token
    return new LoginVO(user, token);
  }

  /**
   * 账号密码登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 用户信息和token
   */
  @Override
  public LoginVO loginByAccount(String username, String password) {
    // 1. 根据用户名查询用户
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getUsername, username);
    User user = getOne(queryWrapper);

    // 2. 校验用户是否存在
    if (user == null) {
      throw new BusinessException("用户名或密码错误");
    }

    // 3. 校验密码是否正确（这里假设密码是明文，实际应使用加密后的密码）
    if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
      throw new BusinessException("用户名或密码错误");
    }

    // 4. 更新最后登录时间
    user.setLastLoginTime(LocalDateTime.now());
    updateById(user);

    // 5. 生成JWT token
    String token = JwtUtil.generateToken(user.getId());

    // 6. 返回用户信息和token
    return new LoginVO(user, token);
  }

  /**
   * 获取当前登录用户信息
   *
   * @param userId 用户ID
   * @return 用户信息
   */
  @Override
  public User getCurrentUser(Long userId) {
    return getById(userId);
  }

  /**
   * 更新用户信息
   *
   * @param user 用户信息
   * @return 更新后的用户信息
   */
  @Override
  public User updateUser(User user) {
    User existingUser = getById(user.getId());
    if (existingUser == null) {
      throw new BusinessException("用户不存在");
    }
    BeanUtils.copyProperties(user, existingUser, "id", "openId", "sessionKey", "password", "createTime",
        "lastLoginTime");
    existingUser.setUpdateTime(LocalDateTime.now());
    updateById(existingUser);
    return existingUser;
  }
}