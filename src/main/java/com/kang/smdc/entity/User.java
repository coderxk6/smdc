package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String openId;

  private String sessionKey;

  private String nickName;

  private String username;

  private String password;

  private String avatarUrl;

  private Integer gender;

  private String city;

  private String province;

  private String country;

  private String phone;

  private LocalDateTime lastLoginTime;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}