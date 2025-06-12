package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.Address;

import java.util.List;

/**
 * 地址服务接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface AddressService extends IService<Address> {

  /**
   * 根据用户ID获取地址列表
   *
   * @param userId 用户ID
   * @return 地址列表
   */
  List<Address> listByUserId(Long userId);

  /**
   * 设置默认地址
   *
   * @param id     地址ID
   * @param userId 用户ID
   */
  void setDefault(Long id, Long userId);
}