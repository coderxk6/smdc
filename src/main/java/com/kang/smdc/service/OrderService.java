package com.kang.smdc.service;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.controller.mini.dto.OrderCreateDTO;
import com.kang.smdc.vo.OrderVO;

public interface OrderService {
  Result<?> createOrder(OrderCreateDTO dto);

  Result<?> payOrder(Long orderId, Integer payMethod);

  Result<?> cancelOrder(Long orderId);

  Result<?> listOrders(Integer page, Integer size);

  Result<OrderVO> getOrderDetail(Long orderId);

  Result<?> getTableInfoByCode(String code);
}