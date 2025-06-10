package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 规格实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@TableName("specification")
public class Specification {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 菜品id
   */
  private Long dishId;

  /**
   * 规格名称
   */
  private String name;

  /**
   * 价格
   */
  private BigDecimal price;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
}