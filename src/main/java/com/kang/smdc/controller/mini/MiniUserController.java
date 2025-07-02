package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.common.util.JwtUtil;
import com.kang.smdc.controller.mini.dto.LoginRequest;
import com.kang.smdc.entity.User;
import com.kang.smdc.service.UserService;
import com.kang.smdc.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 小程序用户控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/mini/user")
@RequiredArgsConstructor
@Api(tags = "小程序用户接口")
public class MiniUserController {

  private final UserService userService;

  /**
   * 微信登录
   */
  @PostMapping("/login")
  @ApiOperation("微信登录")
  public Result<LoginVO> login(@ApiParam("微信登录code") @RequestParam String code) {
    log.info("微信登录：code={}", code);
    LoginVO loginVO = userService.loginByWechat(code);
    return Result.success(loginVO);
  }

  /**
   * 账号密码登录
   * 请求参数：JSON格式的用户名和密码
   * 
   * @param loginRequest 包含用户名和密码的LoginRequest对象
   * @return 统一响应结果，包含用户信息
   */
  @PostMapping("/account/login")
  @ApiOperation("账号密码登录")
  public Result<LoginVO> loginByAccount(@RequestBody LoginRequest loginRequest) {
    log.info("账号密码登录：username={}", loginRequest.getUsername());
    LoginVO loginVO = userService.loginByAccount(loginRequest.getUsername(), loginRequest.getPassword());
    return Result.success(loginVO);
  }

  /**
   * 获取当前用户信息
   * 该接口通过请求头中的token获取用户ID
   * 
   * @param request HttpServletRequest 用于获取请求头中的token
   * @return 统一响应结果，包含用户信息
   */
  @PostMapping("/info")
  @ApiOperation("获取当前用户信息")
  public Result<User> getCurrentUser(HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("获取当前用户信息：token={}", token);

    // 2. 校验Token是否存在
    if (!StringUtils.hasText(token)) {
      return Result.error("token缺失，请重新登录");
    }

    // 3. 解析Token获取用户ID
    Long userId;
    try {
      userId = JwtUtil.getUserId(token);
      if (userId == null) {
        return Result.error("token无效，请重新登录");
      }
    } catch (Exception e) {
      log.error("解析token失败: {}", e.getMessage());
      return Result.error("token解析失败，请重新登录");
    }

    // 4. 根据用户ID查询用户信息
    User user = userService.getById(userId);

    // 5. 校验用户是否存在
    if (user == null) {
      return Result.error("用户不存在或已失效，请重新登录");
    }

    // 6. 成功获取用户信息并返回
    return Result.success(user);
  }

  /**
   * 根据ID获取用户信息（通常用于管理员权限）
   */
  @GetMapping("/info/{id}")
  @ApiOperation("根据ID获取用户信息（管理员权限）")
  public Result<User> getInfo(@ApiParam("用户ID") @PathVariable Long id) {
    log.info("根据ID获取用户信息：id={}", id);
    User user = userService.getById(id);
    return Result.success(user);
  }

  /**
   * 更新用户信息
   */
  @PostMapping("/update")
  @ApiOperation("更新用户信息")
  public Result<Void> update(@ApiParam("用户信息") @RequestBody User user) {
    log.info("更新用户信息：user={}", user);
    userService.updateUser(user);
    return Result.success();
  }
}