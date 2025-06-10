package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜品实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@TableName("dish")
public class Dish {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 菜品名称
   */
  private String name;

  /**
   * 分类id
   */
  private Long categoryId;

  /**
   * 价格
   */
  private BigDecimal price;

  /**
   * 图片
   */
  private String image;

  /**
   * 描述信息
   */
  private String description;

  /**
   * 售卖状态 0:停售 1:起售
   */
  private Integer status;

  /**
   * 排序号
   */
  private Integer sort;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 规格列表
   */
  @TableField(exist = false)
  private List<Specification> specifications;
}