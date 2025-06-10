package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品Mapper接口
 *
 * @author kang
 * @since 2024-01-01
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}