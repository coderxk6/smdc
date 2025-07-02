package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.entity.Address;
import com.kang.smdc.mapper.AddressMapper;
import com.kang.smdc.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地址服务实现类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

  @Override
  public List<Address> listByUserId(Long userId) {
    // 构建查询条件
    LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Address::getUserId, userId)
        .orderByDesc(Address::getIsDefault) // 默认地址排在前面
        .orderByDesc(Address::getUpdateTime); // 更新时间倒序

    // 查询地址列表
    return list(queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void setDefault(Long id, Long userId) {
    // 1. 将该用户的所有地址设置为非默认
    LambdaQueryWrapper<Address> updateWrapper = new LambdaQueryWrapper<>();
    updateWrapper.eq(Address::getUserId, userId);
    Address updateAddress = new Address();
    updateAddress.setIsDefault(false);
    update(updateAddress, updateWrapper);

    // 2. 将指定地址设置为默认
    Address address = new Address();
    address.setId(id);
    address.setIsDefault(true);
    updateById(address);
  }
}