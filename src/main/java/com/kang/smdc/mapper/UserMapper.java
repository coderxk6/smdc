package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author kang
 * @since 2024-01-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}