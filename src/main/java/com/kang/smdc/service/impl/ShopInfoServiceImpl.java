package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.entity.ShopInfo;
import com.kang.smdc.mapper.ShopInfoMapper;
import com.kang.smdc.service.ShopInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopInfoService {

  @Override
  public ShopInfo getInfo() {
    // 获取店铺信息，如果没有则返回默认值
    ShopInfo shopInfo = this.getById(1L);
    if (shopInfo == null) {
      shopInfo = new ShopInfo();
      shopInfo.setId(1L);
      shopInfo.setName("扫码点餐");
      shopInfo.setStatus(1);
      this.save(shopInfo);
    }
    return shopInfo;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(ShopInfo shopInfo) {
    // 检查店铺信息是否存在
    ShopInfo existShopInfo = this.getById(shopInfo.getId());
    if (existShopInfo == null) {
      throw new RuntimeException("店铺信息不存在");
    }
    return this.updateById(shopInfo);
  }
}