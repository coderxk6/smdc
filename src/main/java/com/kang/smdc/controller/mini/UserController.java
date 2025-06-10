 package com.kang.smdc.controller.mini;

 import com.kang.smdc.common.result.Result;
 import com.kang.smdc.entity.User;
 import com.kang.smdc.service.UserService;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import io.swagger.annotations.ApiParam;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.web.bind.annotation.*;

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
 public class UserController {

 private final UserService userService;

 /**
 * 微信登录
 */
 @PostMapping("/login")
 @ApiOperation("微信登录")
 public Result<User> login(@ApiParam("微信登录code") @RequestParam String code) {
 log.info("微信登录：code={}", code);
 User user = userService.loginByWechat(code);
 return Result.success(user);
 }

 /**
 * 账号密码登录
 */
 @PostMapping("/account/login")
 @ApiOperation("账号密码登录")
 public Result<User> loginByAccount(@RequestBody
 com.kang.smdc.controller.mini.dto.LoginRequest loginRequest) {
 log.info("账号密码登录：username={}", loginRequest.getUsername());
 User user = userService.loginByAccount(loginRequest.getUsername(),
 loginRequest.getPassword());
 return Result.success(user);
 }

 /**
 * 获取当前用户信息
 */
 @PostMapping("/info")
 @ApiOperation("获取当前用户信息")
 public Result<User> getCurrentUser(@ApiParam("用户ID") @RequestParam Long
 userId) {
 log.info("获取当前用户信息：userId={}", userId);
 User user = userService.getCurrentUser(userId);
 return Result.success(user);
 }

 /**
 * 根据ID获取用户信息
 */
 @GetMapping("/info/{id}")
 @ApiOperation("根据ID获取用户信息")
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