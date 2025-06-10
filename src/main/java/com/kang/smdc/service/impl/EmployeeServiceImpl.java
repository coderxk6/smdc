package com.kang.smdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.smdc.common.exception.BusinessException;
import com.kang.smdc.entity.Employee;
import com.kang.smdc.mapper.EmployeeMapper;
import com.kang.smdc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 员工Service实现类
 *
 * @author kang
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

  @Override
  public Employee login(String username, String password) {
    // 查询员工
    Employee employee = lambdaQuery()
        .eq(Employee::getUsername, username)
        .one();

    if (employee == null) {
      throw new BusinessException("员工不存在");
    }

    // 验证密码
    String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    if (!employee.getPassword().equals(encryptedPassword)) {
      throw new BusinessException("密码错误");
    }

    return employee;
  }

  @Override
  public Page<Employee> page(Page<Employee> page, String name) {
    // 构建查询条件
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
    if (StringUtils.hasText(name)) {
      queryWrapper.like(Employee::getName, name);
    }
    queryWrapper.orderByDesc(Employee::getCreateTime);

    // 执行分页查询
    return page(page, queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean add(Employee employee) {
    // 检查用户名是否已存在
    long count = lambdaQuery()
        .eq(Employee::getUsername, employee.getUsername())
        .count();
    if (count > 0) {
      throw new BusinessException("用户名已存在");
    }

    // 加密密码
    employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes(StandardCharsets.UTF_8)));

    // 设置创建和更新时间
    employee.setCreateTime(LocalDateTime.now());
    employee.setUpdateTime(LocalDateTime.now());

    return save(employee);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(Employee employee) {
    // 检查员工是否存在
    Employee existEmployee = getById(employee.getId());
    if (existEmployee == null) {
      throw new BusinessException("员工不存在");
    }

    // 如果修改了用户名，检查是否与其他员工重复
    if (!existEmployee.getUsername().equals(employee.getUsername())) {
      long count = lambdaQuery()
          .eq(Employee::getUsername, employee.getUsername())
          .count();
      if (count > 0) {
        throw new BusinessException("用户名已存在");
      }
    }

    // 如果修改了密码，需要加密
    if (StringUtils.hasText(employee.getPassword()) && !employee.getPassword().equals(existEmployee.getPassword())) {
      employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes(StandardCharsets.UTF_8)));
    } else {
      employee.setPassword(existEmployee.getPassword());
    }

    // 设置更新时间
    employee.setUpdateTime(LocalDateTime.now());

    return updateById(employee);
  }

  @Override
  public Employee getById(Long id) {
    Employee employee = super.getById(id);
    if (employee == null) {
      throw new BusinessException("员工不存在");
    }
    return employee;
  }
}