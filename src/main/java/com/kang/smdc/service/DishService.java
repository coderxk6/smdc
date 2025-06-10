package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.entity.Specification;

import java.util.List;

/**
 * 菜品Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface DishService extends IService<Dish> {

  /**
   * 分页查询菜品列表
   *
   * @param page       分页参数
   * @param name       菜品名称（可选）
   * @param categoryId 分类ID（可选）
   * @return 分页结果
   */
  Page<Dish> page(Page<Dish> page, String name, Long categoryId);

  /**
   * 根据分类ID获取菜品列表
   *
   * @param categoryId 分类ID
   * @return 菜品列表
   */
  List<Dish> listByCategoryId(Long categoryId);

  /**
   * 获取菜品详情（包含规格信息）
   *
   * @param id 菜品ID
   * @return 菜品详情
   */
  Dish getDetail(Long id);

  /**
   * 添加菜品（包含规格信息）
   *
   * @param dish           菜品信息
   * @param specifications 规格列表
   * @return 是否成功
   */
  boolean add(Dish dish, List<Specification> specifications);

  /**
   * 更新菜品（包含规格信息）
   *
   * @param dish           菜品信息
   * @param specifications 规格列表
   * @return 是否成功
   */
  boolean update(Dish dish, List<Specification> specifications);

  /**
   * 删除菜品（包含规格信息）
   *
   * @param id 菜品ID
   * @return 是否成功
   */
  boolean delete(Long id);

  /**
   * 更新菜品状态
   *
   * @param id     菜品ID
   * @param status 状态（0停售 1起售）
   * @return 是否成功
   */
  boolean updateStatus(Long id, Integer status);

  /**
   * 搜索菜品
   * @param keyword 关键词
   * @return 菜品列表
   */
  List<Dish> search(String keyword);

  /**
   * 获取推荐菜品
   * @param limit 限制数量
   * @return 推荐菜品列表
   */
  List<Dish> getRecommendDishes(Integer limit);

  /**
   * 获取热门菜品
   * @param limit 限制数量
   * @return 热门菜品列表
   */
  List<Dish> getHotDishes(Integer limit);

  /**
   * 获取新品菜品
   * @param limit 限制数量
   * @return 新品菜品列表
   */
  List<Dish> getNewDishes(Integer limit);
}