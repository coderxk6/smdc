package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("order_detail")
public class OrderDetail {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long orderId;
  private Long dishId;
  private Long specificationId;
  private Integer number;
  private BigDecimal amount;
  private Date createTime;
}