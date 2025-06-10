package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.Category;

import java.util.List;

/**
 * 菜品分类Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface CategoryService extends IService<Category> {

  /**
   * 分页查询分类列表
   *
   * @param page 分页参数
   * @param name 分类名称（可选）
   * @return 分页结果
   */
  Page<Category> page(Page<Category> page, String name);

  /**
   * 获取所有分类列表（不分页）
   *
   * @return 分类列表
   */
  List<Category> list();

  /**
   * 添加分类
   *
   * @param category 分类信息
   * @return 是否成功
   */
  boolean add(Category category);

  /**
   * 更新分类
   *
   * @param category 分类信息
   * @return 是否成功
   */
  boolean update(Category category);

  /**
   * 删除分类
   *
   * @param id 分类ID
   * @return 是否成功
   */
  boolean delete(Long id);
}