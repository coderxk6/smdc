package com.kang.smdc.controller.mini.dto;

import lombok.Data;

@Data
public class LoginRequest {
  private String username;
  private String password;
}