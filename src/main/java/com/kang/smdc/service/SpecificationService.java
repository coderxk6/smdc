package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.Specification;

import java.util.List;

/**
 * 规格Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface SpecificationService extends IService<Specification> {

  /**
   * 根据菜品ID查询规格列表
   *
   * @param dishId 菜品ID
   * @return 规格列表
   */
  List<Specification> listByDishId(Long dishId);

  /**
   * 批量添加规格
   *
   * @param specifications 规格列表
   * @return 是否成功
   */
  boolean saveBatch(List<Specification> specifications);

  /**
   * 批量更新规格
   *
   * @param specifications 规格列表
   * @return 是否成功
   */
  boolean updateBatch(List<Specification> specifications);

  /**
   * 根据菜品ID删除规格
   *
   * @param dishId 菜品ID
   * @return 是否成功
   */
  boolean removeByDishId(Long dishId);
}