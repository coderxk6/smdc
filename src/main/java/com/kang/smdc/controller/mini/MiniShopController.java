package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.ShopInfo;
import com.kang.smdc.service.ShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mini/shop")
@RequiredArgsConstructor
@Api(tags = "小程序店铺信息接口")
public class MiniShopController {

    private final ShopInfoService shopInfoService;

    @GetMapping("/info")
    @ApiOperation("获取店铺信息")
    public Result<ShopInfo> getInfo() {
        log.info("小程序获取店铺信息");
        return Result.success(shopInfoService.getInfo());
    }
} 