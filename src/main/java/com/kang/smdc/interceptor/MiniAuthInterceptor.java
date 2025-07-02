package com.kang.smdc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.User;
import com.kang.smdc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MiniAuthInterceptor implements HandlerInterceptor {

  @Lazy
  private final UserService userService;
  private final ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 获取用户ID
    String userId = request.getHeader("user-id");
    if (!StringUtils.hasText(userId)) {
      handleError(response, "未登录");
      return false;
    }

    try {
      // 验证用户是否存在
      User user = userService.getById(Long.parseLong(userId));
      if (user == null) {
        handleError(response, "用户不存在");
        return false;
      }

      // 将用户信息存入request
      request.setAttribute("user", user);
      return true;
    } catch (NumberFormatException e) {
      handleError(response, "用户ID格式错误");
      return false;
    }
  }

  private void handleError(HttpServletResponse response, String message) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(Result.error(message)));
  }
}