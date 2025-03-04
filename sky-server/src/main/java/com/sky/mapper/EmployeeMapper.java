package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee
     */
    void insertEmp(Employee employee);

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> selectAll(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 员工账号启用禁用
     *
     * @param emp
     */
    void updateEmp(Employee emp);
}
