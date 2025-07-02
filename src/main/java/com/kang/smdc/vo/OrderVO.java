package com.kang.smdc.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    private Long id;
    private String number;
    private String tableName;
    private BigDecimal amount;
    private Integer status;
    private Integer payStatus;
    private Integer payMethod;
    private String remark;
    private Date createTime;
    private List<Item> items;

    @Data
    public static class Item {
        private String dishName;
        private String specification;
        private Integer number;
        private BigDecimal amount;
    }
} 