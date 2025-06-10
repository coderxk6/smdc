package com.kang.smdc.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理接口")
public class AdminDishController {

  @Autowired
  private DishService dishService;

  @GetMapping("/page")
  @ApiOperation("菜品分页查询")
  public Result<Page<Dish>> page(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize,
      String name,
      Long categoryId) {
    Page<Dish> pageInfo = new Page<>(page, pageSize);
    Page<Dish> result = dishService.page(pageInfo, name, categoryId);
    return Result.success(result);
  }

  @GetMapping("/info/{id}")
  @ApiOperation("获取菜品信息")
  public Result<Dish> getInfo(@PathVariable Long id) {
    Dish dish = dishService.getDetail(id);
    return Result.success(dish);
  }

  @PostMapping("/add")
  @ApiOperation("添加菜品")
  public Result<Boolean> add(@RequestBody Dish dish) {
    boolean success = dishService.add(dish, dish.getSpecifications());
    return success ? Result.success(true) : Result.error("添加菜品失败");
  }

  @PostMapping("/update")
  @ApiOperation("更新菜品")
  public Result<Boolean> update(@RequestBody Dish dish) {
    boolean success = dishService.update(dish, dish.getSpecifications());
    return success ? Result.success(true) : Result.error("更新菜品失败");
  }

  @PostMapping("/delete")
  @ApiOperation("删除菜品")
  public Result<Boolean> delete(@RequestParam Long id) {
    boolean success = dishService.delete(id);
    return success ? Result.success(true) : Result.error("删除菜品失败");
  }

  @PostMapping("/status")
  @ApiOperation("更新菜品状态")
  public Result<Boolean> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    boolean success = dishService.updateStatus(id, status);
    return success ? Result.success(true) : Result.error("更新菜品状态失败");
  }
}