package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.TableInfo;

/**
 * 桌位Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface TableInfoService extends IService<TableInfo> {

  /**
   * 分页查询桌位列表
   *
   * @param page 分页参数
   * @param name 桌位名称（可选）
   * @return 分页结果
   */
  Page<TableInfo> page(Page<TableInfo> page, String name);

  /**
   * 添加桌位
   *
   * @param tableInfo 桌位信息
   * @return 是否成功
   */
  boolean add(TableInfo tableInfo);

  /**
   * 更新桌位信息
   *
   * @param tableInfo 桌位信息
   * @return 是否成功
   */
  boolean update(TableInfo tableInfo);

  /**
   * 删除桌位
   *
   * @param id 桌位ID
   * @return 是否成功
   */
  boolean delete(Long id);

  /**
   * 生成桌位二维码
   *
   * @param id 桌位ID
   * @return 二维码路径
   */
  String generateQrCode(Long id);
}