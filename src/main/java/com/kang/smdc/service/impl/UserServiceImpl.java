package com.kang.smdc.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.common.exception.BusinessException;
import com.kang.smdc.entity.User;
import com.kang.smdc.mapper.UserMapper;
import com.kang.smdc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

  private final WxMaService wxMaService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public User loginByWechat(String code) {
    try {
      // 获取微信用户信息
      WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
      String openId = session.getOpenid();
      String sessionKey = session.getSessionKey();

      // 查询用户是否存在
      User user = getOne(new LambdaQueryWrapper<User>()
          .eq(User::getOpenId, openId));

      if (user == null) {
        // 用户不存在，创建新用户
        user = new User();
        user.setOpenId(openId);
        user.setSessionKey(sessionKey);
        user.setLastLoginTime(LocalDateTime.now());
        save(user);
      } else {
        // 更新用户登录信息
        user.setSessionKey(sessionKey);
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);
      }

      return user;
    } catch (Exception e) {
      log.error("微信登录失败", e);
      throw new BusinessException("微信登录失败：" + e.getMessage());
    }
  }

  @Override
  public User loginByAccount(String username, String password) {
    User user = getOne(new LambdaQueryWrapper<User>()
        .eq(User::getUsername, username)
        .eq(User::getPassword, password));
    if (user == null) {
      throw new BusinessException("用户名或密码错误");
    }
    user.setLastLoginTime(LocalDateTime.now());
    updateById(user);
    return user;
  }

  @Override
  public User getCurrentUser(Long userId) {
    User user = getById(userId);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    return user;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public User updateUser(User user) {
    // 验证用户是否存在
    User existUser = getById(user.getId());
    if (existUser == null) {
      throw new BusinessException("用户不存在");
    }

    // 更新用户信息
    user.setUpdateTime(LocalDateTime.now());
    updateById(user);

    return getById(user.getId());
  }

  /**
   * 更新微信用户信息
   * 
   * @param openId     微信openId
   * @param wxUserInfo 微信用户信息
   * @return 更新后的用户信息
   */
  @Transactional(rollbackFor = Exception.class)
  public User updateUserInfo(String openId, WxMaUserInfo wxUserInfo) {
    User user = getOne(new LambdaQueryWrapper<User>()
        .eq(User::getOpenId, openId));
    if (user == null) {
      throw new BusinessException("用户不存在");
    }

    // 更新用户信息
    user.setNickName(wxUserInfo.getNickName());
    user.setAvatarUrl(wxUserInfo.getAvatarUrl());
    // 转换性别类型：0-未知，1-男，2-女
    user.setGender(Integer.valueOf(wxUserInfo.getGender()));
    user.setCountry(wxUserInfo.getCountry());
    user.setProvince(wxUserInfo.getProvince());
    user.setCity(wxUserInfo.getCity());
    user.setUpdateTime(LocalDateTime.now());

    updateById(user);
    return user;
  }
}