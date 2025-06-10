package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Category;
import com.kang.smdc.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序分类控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/mini/category")
@Api(tags = "小程序分类接口")
public class MiniCategoryController {

  @Autowired
  private CategoryService categoryService;

  /**
   * 获取分类列表
   */
  @GetMapping("/list")
  @ApiOperation("获取分类列表")
  public Result<List<Category>> list() {
    log.info("获取分类列表");
    return Result.success(categoryService.list());
  }
}