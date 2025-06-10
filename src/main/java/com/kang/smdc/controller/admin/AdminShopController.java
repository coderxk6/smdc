package com.kang.smdc.controller.admin;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.ShopInfo;
import com.kang.smdc.service.ShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/shop")
@RequiredArgsConstructor
@Api(tags = "店铺信息管理接口")
public class AdminShopController {

  private final ShopInfoService shopInfoService;

  @GetMapping("/info")
  @ApiOperation("获取店铺信息")
  public Result<ShopInfo> getInfo() {
    log.info("获取店铺信息");
    return Result.success(shopInfoService.getInfo());
  }

  @PostMapping("/update")
  @ApiOperation("更新店铺信息")
  public Result<Boolean> update(@RequestBody ShopInfo shopInfo) {
    log.info("更新店铺信息：{}", shopInfo);
    boolean success = shopInfoService.update(shopInfo);
    return success ? Result.success(true) : Result.error("更新店铺信息失败");
  }
}