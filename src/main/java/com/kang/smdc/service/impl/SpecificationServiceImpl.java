package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.entity.Specification;
import com.kang.smdc.mapper.SpecificationMapper;
import com.kang.smdc.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 规格Service实现类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification>
    implements SpecificationService {

  @Override
  public List<Specification> listByDishId(Long dishId) {
    LambdaQueryWrapper<Specification> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Specification::getDishId, dishId);
    return list(queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveBatch(List<Specification> specifications) {
    if (specifications == null || specifications.isEmpty()) {
      return true;
    }
    specifications.forEach(spec -> {
      spec.setCreateTime(LocalDateTime.now());
      spec.setUpdateTime(LocalDateTime.now());
    });
    return saveBatch(specifications);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateBatch(List<Specification> specifications) {
    if (specifications == null || specifications.isEmpty()) {
      return true;
    }
    specifications.forEach(spec -> spec.setUpdateTime(LocalDateTime.now()));
    return updateBatchById(specifications);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeByDishId(Long dishId) {
    LambdaQueryWrapper<Specification> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Specification::getDishId, dishId);
    return remove(queryWrapper);
  }
}