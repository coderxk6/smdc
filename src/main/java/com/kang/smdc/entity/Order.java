package com.kang.smdc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String number;
    private Long userId;
    private Long tableId;
    private BigDecimal amount;
    private Integer status;
    private Integer payMethod;
    private Integer payStatus;
    private String remark;
    private Date createTime;
    private Date updateTime;
} 