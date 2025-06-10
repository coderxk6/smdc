package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.entity.Dish;
import com.kang.smdc.entity.Specification;
import com.kang.smdc.mapper.DishMapper;
import com.kang.smdc.mapper.SpecificationMapper;
import com.kang.smdc.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

  @Autowired
  private SpecificationMapper specificationMapper;

  @Override
  public Page<Dish> page(Page<Dish> page, String name, Long categoryId) {
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(StringUtils.hasText(name), Dish::getName, name);
    queryWrapper.eq(categoryId != null, Dish::getCategoryId, categoryId);
    queryWrapper.orderByAsc(Dish::getSort);
    return this.page(page, queryWrapper);
  }

  @Override
  public List<Dish> listByCategoryId(Long categoryId) {
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Dish::getCategoryId, categoryId);
    queryWrapper.eq(Dish::getStatus, 1);
    queryWrapper.orderByAsc(Dish::getSort);
    return this.list(queryWrapper);
  }

  @Override
  public Dish getDetail(Long id) {
    // 获取菜品基本信息
    Dish dish = this.getById(id);
    if (dish != null) {
      // 获取菜品规格信息
      LambdaQueryWrapper<Specification> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Specification::getDishId, id);
      List<Specification> specifications = specificationMapper.selectList(queryWrapper);
      // 将规格信息设置到菜品对象中
      // 注意：这里需要Dish实体类中包含一个List<Specification>字段才能设置
      // 如果Dish实体类没有该字段，需要重新设计VO或者在前端处理
      // 这里假设Dish实体类将包含这个字段
      dish.setSpecifications(specifications);
    }
    return dish;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean add(Dish dish, List<Specification> specifications) {
    // 保存菜品基本信息
    boolean success = this.save(dish);
    if (success && specifications != null && !specifications.isEmpty()) {
      // 设置菜品ID并保存规格信息
      for (Specification specification : specifications) {
        specification.setDishId(dish.getId());
      }
      success = specifications.stream().allMatch(specification -> specificationMapper.insert(specification) > 0);
    }
    return success;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(Dish dish, List<Specification> specifications) {
    // 更新菜品基本信息
    boolean success = this.updateById(dish);
    if (success && specifications != null) {
      // 删除原有规格
      LambdaQueryWrapper<Specification> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Specification::getDishId, dish.getId());
      specificationMapper.delete(queryWrapper);
      // 保存新规格
      if (!specifications.isEmpty()) {
        for (Specification specification : specifications) {
          specification.setDishId(dish.getId());
        }
        success = specifications.stream().allMatch(specification -> specificationMapper.insert(specification) > 0);
      }
    }
    return success;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(Long id) {
    // 删除菜品基本信息
    boolean success = this.removeById(id);
    if (success) {
      // 删除菜品规格信息
      LambdaQueryWrapper<Specification> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Specification::getDishId, id);
      specificationMapper.delete(queryWrapper);
    }
    return success;
  }

  @Override
  public boolean updateStatus(Long id, Integer status) {
    Dish dish = new Dish();
    dish.setId(id);
    dish.setStatus(status);
    return this.updateById(dish);
  }

  @Override
  public List<Dish> search(String keyword) {
    if (!StringUtils.hasText(keyword)) {
      return Collections.emptyList();
    }
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(Dish::getName, keyword);
    queryWrapper.eq(Dish::getStatus, 1);
    queryWrapper.orderByDesc(Dish::getCreateTime);
    return this.list(queryWrapper);
  }

  @Override
  public List<Dish> getRecommendDishes(Integer limit) {
    // 推荐菜品这里简单实现为按排序号降序的前N个菜品
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Dish::getStatus, 1);
    queryWrapper.orderByDesc(Dish::getSort);
    queryWrapper.last("LIMIT " + (limit != null ? limit : 6));
    return this.list(queryWrapper);
  }

  @Override
  public List<Dish> getHotDishes(Integer limit) {
    // 热门菜品这里简单实现为按创建时间降序的前N个菜品
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Dish::getStatus, 1);
    queryWrapper.orderByDesc(Dish::getCreateTime);
    queryWrapper.last("LIMIT " + (limit != null ? limit : 6));
    return this.list(queryWrapper);
  }

  @Override
  public List<Dish> getNewDishes(Integer limit) {
    // 新品菜品这里简单实现为按创建时间降序的前N个菜品
    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Dish::getStatus, 1);
    queryWrapper.orderByDesc(Dish::getCreateTime);
    queryWrapper.last("LIMIT " + (limit != null ? limit : 6));
    return this.list(queryWrapper);
  }
}