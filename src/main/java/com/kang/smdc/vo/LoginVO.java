package com.kang.smdc.vo;

import com.kang.smdc.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回视图对象
 *
 * @author kang
 * @since 2024-01-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LoginVO对象", description = "登录返回信息")
public class LoginVO {

  @ApiModelProperty("用户信息")
  private User userInfo;

  @ApiModelProperty("认证token")
  private String token;
}