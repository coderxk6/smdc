package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 地址实体类
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@TableName("address")
@ApiModel(value = "地址对象", description = "地址信息")
public class Address {

  @ApiModelProperty("主键")
  @TableId(type = IdType.AUTO)
  private Long id;

  @ApiModelProperty("用户ID")
  private Long userId;

  @ApiModelProperty("收货人")
  private String receiver;

  @ApiModelProperty("手机号")
  private String phone;

  @ApiModelProperty("省份")
  private String province;

  @ApiModelProperty("城市")
  private String city;

  @ApiModelProperty("区县")
  private String district;

  @ApiModelProperty("详细地址")
  private String detail;

  @ApiModelProperty("标签")
  private String label;

  @ApiModelProperty("是否默认地址")
  private Boolean isDefault;

  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty("更新时间")
  private LocalDateTime updateTime;
}