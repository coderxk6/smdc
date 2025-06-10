package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 桌位Mapper接口
 *
 * @author kang
 * @since 2024-01-01
 */
@Mapper
public interface TableInfoMapper extends BaseMapper<TableInfo> {
}