package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.Address;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址Mapper接口
 *
 * @author kang
 * @since 2024-01-01
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}