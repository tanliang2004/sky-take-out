package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 员工账号启用和禁用
     *
     * @param status
     * @param id
     */
    void startOrShut(Integer status, Long id);

    /**
     * 根据id查询员工
     * @param id
     */
    Employee queryById(Integer id);

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    void edit(EmployeeDTO employeeDTO);
}
