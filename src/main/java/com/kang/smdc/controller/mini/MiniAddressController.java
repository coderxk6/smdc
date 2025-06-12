package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.common.util.JwtUtil;
import com.kang.smdc.entity.Address;
import com.kang.smdc.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 小程序地址管理控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/mini/user/address")
@RequiredArgsConstructor
@Api(tags = "小程序地址管理接口")
public class MiniAddressController {

  private final AddressService addressService;

  /**
   * 获取当前用户的地址列表
   */
  @GetMapping("/list")
  @ApiOperation("获取地址列表")
  public Result<List<Address>> list(HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("获取地址列表：token={}", token);

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

    // 4. 获取地址列表
    List<Address> addresses = addressService.listByUserId(userId);
    return Result.success(addresses);
  }

  /**
   * 添加地址
   */
  @PostMapping("/add")
  @ApiOperation("添加地址")
  public Result<Void> add(@RequestBody Address address, HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("添加地址：token={}, address={}", token, address);

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

    // 4. 设置用户ID并保存地址
    address.setUserId(userId);
    addressService.save(address);
    return Result.success();
  }

  /**
   * 更新地址
   */
  @PostMapping("/update")
  @ApiOperation("更新地址")
  public Result<Void> update(@RequestBody Address address, HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("更新地址：token={}, address={}", token, address);

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

    // 4. 校验地址是否属于当前用户
    Address oldAddress = addressService.getById(address.getId());
    if (oldAddress == null || !oldAddress.getUserId().equals(userId)) {
      return Result.error("地址不存在或无权限修改");
    }

    // 5. 更新地址
    address.setUserId(userId);
    addressService.updateById(address);
    return Result.success();
  }

  /**
   * 删除地址
   */
  @PostMapping("/delete/{id}")
  @ApiOperation("删除地址")
  public Result<Void> delete(@ApiParam("地址ID") @PathVariable Long id, HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("删除地址：token={}, id={}", token, id);

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

    // 4. 校验地址是否属于当前用户
    Address address = addressService.getById(id);
    if (address == null || !address.getUserId().equals(userId)) {
      return Result.error("地址不存在或无权限删除");
    }

    // 5. 删除地址
    addressService.removeById(id);
    return Result.success();
  }

  /**
   * 设置默认地址
   */
  @PostMapping("/default/{id}")
  @ApiOperation("设置默认地址")
  public Result<Void> setDefault(@ApiParam("地址ID") @PathVariable Long id, HttpServletRequest request) {
    // 1. 从请求头中获取Token
    String token = request.getHeader("token");
    log.info("设置默认地址：token={}, id={}", token, id);

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

    // 4. 校验地址是否属于当前用户
    Address address = addressService.getById(id);
    if (address == null || !address.getUserId().equals(userId)) {
      return Result.error("地址不存在或无权限修改");
    }

    // 5. 设置默认地址
    addressService.setDefault(id, userId);
    return Result.success();
  }
}