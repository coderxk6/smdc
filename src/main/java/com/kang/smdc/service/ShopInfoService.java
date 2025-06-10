package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.ShopInfo;

public interface ShopInfoService extends IService<ShopInfo> {
  /**
   * 获取店铺信息
   * 
   * @return 店铺信息
   */
  ShopInfo getInfo();

  /**
   * 更新店铺信息
   * 
   * @param shopInfo 店铺信息
   * @return 是否成功
   */
  boolean update(ShopInfo shopInfo);
}