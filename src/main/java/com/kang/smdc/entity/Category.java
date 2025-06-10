package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 菜品分类实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@TableName("category")
public class Category {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 分类名称
   */
  private String name;

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
}