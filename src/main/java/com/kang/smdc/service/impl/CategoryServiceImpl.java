package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.common.exception.BusinessException;
import com.kang.smdc.entity.Category;
import com.kang.smdc.mapper.CategoryMapper;
import com.kang.smdc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜品分类Service实现类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

  @Override
  public Page<Category> page(Page<Category> page, String name) {
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(StringUtils.hasText(name), Category::getName, name);
    queryWrapper.orderByAsc(Category::getSort);
    return this.page(page, queryWrapper);
  }

  @Override
  public List<Category> list() {
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByAsc(Category::getSort);
    return this.list(queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean add(Category category) {
    // 检查分类名称是否已存在
    long count = lambdaQuery()
        .eq(Category::getName, category.getName())
        .count();
    if (count > 0) {
      throw new BusinessException("分类名称已存在");
    }
    category.setCreateTime(LocalDateTime.now());
    category.setUpdateTime(LocalDateTime.now());
    // 设置排序号，如果没有设置则默认为0
    if (category.getSort() == null) {
      category.setSort(0);
    }
    return save(category);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(Category category) {
    // 检查分类是否存在
    Category existCategory = getById(category.getId());
    if (existCategory == null) {
      throw new BusinessException("分类不存在");
    }

    // 如果修改了名称，检查是否与其他分类重复
    if (!existCategory.getName().equals(category.getName())) {
      long count = lambdaQuery()
          .eq(Category::getName, category.getName())
          .count();
      if (count > 0) {
        throw new BusinessException("分类名称已存在");
      }
    }
    category.setUpdateTime(LocalDateTime.now());
    return updateById(category);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(Long id) {
    // 检查分类是否存在
    Category category = getById(id);
    if (category == null) {
      throw new BusinessException("分类不存在");
    }

    // TODO: 检查该分类下是否有菜品，如果有则不允许删除
    // 在菜品模块开发完成后补充此处的逻辑

    return removeById(id);
  }
}