package com.kang.smdc.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Category;
import com.kang.smdc.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 管理后台分类控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
@Api(tags = "分类管理接口")
public class AdminCategoryController {

  private final CategoryService categoryService;

  /**
   * 分页查询分类列表
   */
  @GetMapping("/page")
  @ApiOperation("分类分页查询")
  public Result<Page<Category>> page(
      @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
      @ApiParam("每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
      @ApiParam("分类名称") String name) {
    log.info("分页查询分类列表：page={}, pageSize={}, name={}", page, pageSize, name);
    Page<Category> pageInfo = new Page<>(page, pageSize);
    Page<Category> result = categoryService.page(pageInfo, name);
    return Result.success(result);
  }

  /**
   * 查询所有分类列表
   */
  @GetMapping("/list")
  @ApiOperation("分类列表查询")
  public Result<java.util.List<Category>> list() {
    log.info("查询所有分类列表");
    return Result.success(categoryService.list());
  }

  /**
   * 添加分类
   */
  @PostMapping("/add")
  @ApiOperation("添加分类")
  public Result<Boolean> add(@ApiParam("分类信息") @RequestBody Category category) {
    log.info("添加分类：category={}", category);
    boolean success = categoryService.add(category);
    return success ? Result.success(true) : Result.error("添加分类失败");
  }

  /**
   * 更新分类
   */
  @PostMapping("/update")
  @ApiOperation("更新分类")
  public Result<Boolean> update(@ApiParam("分类信息") @RequestBody Category category) {
    log.info("更新分类：category={}", category);
    boolean success = categoryService.update(category);
    return success ? Result.success(true) : Result.error("更新分类失败");
  }

  /**
   * 删除分类
   */
  @PostMapping("/delete")
  @ApiOperation("删除分类")
  public Result<Boolean> delete(@ApiParam("分类ID") @RequestParam Long id) {
    log.info("删除分类：id={}", id);
    boolean success = categoryService.delete(id);
    return success ? Result.success(true) : Result.error("删除分类失败");
  }
}