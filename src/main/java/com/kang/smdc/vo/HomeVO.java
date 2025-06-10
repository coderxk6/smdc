package com.kang.smdc.vo;

import com.kang.smdc.entity.Category;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.entity.ShopInfo;
import lombok.Data;

import java.util.List;

@Data
public class HomeVO {
  /**
   * 店铺信息
   */
  private ShopInfo shopInfo;

  /**
   * 分类列表
   */
  private List<Category> categories;

  /**
   * 推荐菜品
   */
  private List<Dish> recommendDishes;

  /**
   * 热门菜品
   */
  private List<Dish> hotDishes;

  /**
   * 新品菜品
   */
  private List<Dish> newDishes;
}