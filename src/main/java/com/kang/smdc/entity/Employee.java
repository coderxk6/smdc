package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 员工实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("employee")
public class Employee {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String username;

  private String password;

  private String name;

  private String phone;

  private Integer role;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}