package com.kang.smdc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Employee;
import com.kang.smdc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理端权限拦截器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {

  private final EmployeeService employeeService;
  private final ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 获取员工ID
    String employeeId = request.getHeader("employee-id");
    if (!StringUtils.hasText(employeeId)) {
      handleError(response, "未登录");
      return false;
    }

    try {
      // 验证员工是否存在
      Employee employee = employeeService.getById(Long.parseLong(employeeId));
      if (employee == null) {
        handleError(response, "员工不存在");
        return false;
      }

      // 将员工信息存入request
      request.setAttribute("employee", employee);
      return true;
    } catch (NumberFormatException e) {
      handleError(response, "员工ID格式错误");
      return false;
    }
  }

  /**
   * 处理错误响应
   */
  private void handleError(HttpServletResponse response, String message) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(Result.error(message)));
  }
}