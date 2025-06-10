package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序菜品控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/mini/dish")
@Api(tags = "小程序菜品接口")
public class MiniDishController {

  @Autowired
  private DishService dishService;

  /**
   * 根据分类ID获取菜品列表
   */
  @GetMapping("/list/{categoryId}")
  @ApiOperation("根据分类ID获取菜品列表")
  public Result<List<Dish>> listByCategoryId(@PathVariable Long categoryId) {
    log.info("根据分类ID获取菜品列表：categoryId={}", categoryId);
    return Result.success(dishService.listByCategoryId(categoryId));
  }

  /**
   * 获取菜品详情
   */
  @GetMapping("/detail/{id}")
  @ApiOperation("获取菜品详情")
  public Result<Dish> getDetail(@PathVariable Long id) {
    log.info("获取菜品详情：id={}", id);
    return Result.success(dishService.getDetail(id));
  }
}