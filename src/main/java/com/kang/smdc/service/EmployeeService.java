package com.kang.smdc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.smdc.entity.Employee;

/**
 * 员工Service接口
 *
 * @author kang
 * @since 2024-01-01
 */
public interface EmployeeService extends IService<Employee> {

  /**
   * 员工登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 员工信息
   */
  Employee login(String username, String password);

  /**
   * 分页查询员工列表
   *
   * @param page 分页参数
   * @param name 员工姓名（可选）
   * @return 分页结果
   */
  Page<Employee> page(Page<Employee> page, String name);

  /**
   * 添加员工
   *
   * @param employee 员工信息
   * @return 是否成功
   */
  boolean add(Employee employee);

  /**
   * 更新员工信息
   *
   * @param employee 员工信息
   * @return 是否成功
   */
  boolean update(Employee employee);

  /**
   * 根据ID获取员工信息
   *
   * @param id 员工ID
   * @return 员工信息
   */
  Employee getById(Long id);
}