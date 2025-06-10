package com.kang.smdc.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.smdc.common.result.Result;
import com.kang.smdc.entity.Employee;
import com.kang.smdc.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理控制器
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
@RequiredArgsConstructor
@Api(tags = "员工管理接口")
public class AdminEmployeeController {

  private final EmployeeService employeeService;

  /**
   * 员工登录
   */
  @PostMapping("/login")
  @ApiOperation("员工登录")
  public Result<Employee> login(
      @ApiParam("用户名") @RequestParam String username,
      @ApiParam("密码") @RequestParam String password) {
    log.info("员工登录：username={}", username);
    Employee employee = employeeService.login(username, password);
    return Result.success(employee);
  }

  /**
   * 获取员工信息
   */
  @GetMapping("/info/{id}")
  @ApiOperation("获取员工信息")
  public Result<Employee> getInfo(@ApiParam("员工ID") @PathVariable Long id) {
    log.info("获取员工信息：id={}", id);
    Employee employee = employeeService.getById(id);
    return Result.success(employee);
  }

  /**
   * 分页查询员工列表
   */
  @GetMapping("/page")
  @ApiOperation("分页查询员工列表")
  public Result<Page<Employee>> page(
      @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
      @ApiParam("每页数量") @RequestParam(defaultValue = "10") Integer size,
      @ApiParam("员工姓名") @RequestParam(required = false) String name) {
    log.info("分页查询员工列表：page={}, size={}, name={}", page, size, name);
    Page<Employee> pageInfo = new Page<>(page, size);
    Page<Employee> result = employeeService.page(pageInfo, name);
    return Result.success(result);
  }

  /**
   * 添加员工
   */
  @PostMapping("/add")
  @ApiOperation("添加员工")
  public Result<Void> add(@ApiParam("员工信息") @RequestBody Employee employee) {
    log.info("添加员工：employee={}", employee);
    employeeService.add(employee);
    return Result.success();
  }

  /**
   * 更新员工信息
   */
  @PostMapping("/update")
  @ApiOperation("更新员工信息")
  public Result<Void> update(@ApiParam("员工信息") @RequestBody Employee employee) {
    log.info("更新员工信息：employee={}", employee);
    employeeService.update(employee);
    return Result.success();
  }
}