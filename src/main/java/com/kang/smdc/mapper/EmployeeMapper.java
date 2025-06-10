package com.kang.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.smdc.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工Mapper接口
 *
 * @author kang
 * @since 2024-01-01
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}