package com.kang.smdc.controller.mini.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCreateDTO {
  @NotNull
  private Long tableId;
  @NotNull
  private List<Item> items;
  private String remark;

  @Data
  public static class Item {
    @NotNull
    private Long dishId;
    private Long specificationId;
    @NotNull
    private Integer number;
  }
}