package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}