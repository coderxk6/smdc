package com.kang.smdc.service;

import com.kang.smdc.entity.User;
import com.kang.smdc.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  public void testLoginByAccount() {
    // 测试账号密码登录
    User user = userService.loginByAccount("test", "123456");
    assertNotNull(user);
    assertEquals("test", user.getUsername());
    assertEquals("测试用户", user.getNickName());
  }

  @Test
  public void testGetCurrentUser() {
    // 测试获取当前用户信息
    User user = userService.getCurrentUser(9999L);
    assertNotNull(user);
    assertEquals(9999L, user.getId());
    assertEquals("测试用户", user.getNickName());
  }

  @Test
  public void testUpdateUser() {
    // 测试更新用户信息
    User user = userService.getCurrentUser(9999L);
    user.setNickName("新昵称");
    user.setPhone("13900139000");
    userService.updateUser(user);

    // 验证更新结果
    User updatedUser = userService.getCurrentUser(9999L);
    assertEquals("新昵称", updatedUser.getNickName());
    assertEquals("13900139000", updatedUser.getPhone());
  }
}