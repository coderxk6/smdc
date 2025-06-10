package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 桌位实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("table_info")
public class TableInfo {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 桌位名称
   */
  private String name;

  /**
   * 桌位二维码
   */
  private String code;

  /**
   * 状态 0:空闲 1:使用中
   */
  private Integer status;

  /**
   * 容纳人数
   */
  private Integer capacity;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
}