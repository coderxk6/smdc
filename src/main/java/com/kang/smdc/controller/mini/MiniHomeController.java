package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Category;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.entity.ShopInfo;
import com.kang.smdc.service.CategoryService;
import com.kang.smdc.service.DishService;
import com.kang.smdc.service.ShopInfoService;
import com.kang.smdc.vo.HomeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mini/home")
@RequiredArgsConstructor
@Api(tags = "小程序首页接口")
public class MiniHomeController {

  private final ShopInfoService shopInfoService;
  private final CategoryService categoryService;
  private final DishService dishService;

  @GetMapping("/data")
  @ApiOperation("获取首页数据")
  public Result<HomeVO> getHomeData() {
    log.info("获取首页数据");
    HomeVO homeVO = new HomeVO();

    // 获取店铺信息
    ShopInfo shopInfo = shopInfoService.getInfo();
    homeVO.setShopInfo(shopInfo);

    // 获取分类列表
    List<Category> categories = categoryService.list();
    homeVO.setCategories(categories);

    // 获取推荐菜品（默认6个）
    List<Dish> recommendDishes = dishService.getRecommendDishes(6);
    homeVO.setRecommendDishes(recommendDishes);

    // 获取热门菜品（默认6个）
    List<Dish> hotDishes = dishService.getHotDishes(6);
    homeVO.setHotDishes(hotDishes);

    // 获取新品菜品（默认6个）
    List<Dish> newDishes = dishService.getNewDishes(6);
    homeVO.setNewDishes(newDishes);

    return Result.success(homeVO);
  }
}