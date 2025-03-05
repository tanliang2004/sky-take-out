package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传过来的铭文密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //创建实体类employee
        Employee employee = new Employee();
        //将employeeDTO类拷贝到employee
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置员工初始化密码（进行MD5加密）
        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(password);
        //设置员工账号状态 1：启用 0：禁用
        employee.setStatus(StatusConstant.ENABLE);
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //设置修改时间
        employee.setUpdateTime(LocalDateTime.now());
        // 获取创建和修改员工的ID,使用ThreadLocal空间
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        log.info("新增员工：{}",employee);
        //调用Mapper新增员工方法
        employeeMapper.insertEmp(employee);
    }

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        //使用分页插件对数据进行分页处理
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        //调用Mapper分页查询方法
        Page<Employee> page = employeeMapper.selectAll(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total, result);
    }

    /**
     * 员工账号启用和禁用
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrShut(Integer status, Long id) {
        //为了代码简洁，将所有对员工信息修改的操作都使用一条SQL命令
        //使用动态SQL对员工信息进行修改
        Employee emp = Employee.builder().status(status).id(id).build();
        employeeMapper.updateEmp(emp);
    }

    /**
     * 根据id查询员工
     * @param id
     */
    @Override
    public Employee queryById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        return employee;
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    @Override
    public void edit(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //将employeeDTO拷贝到employee
        BeanUtils.copyProperties(employeeDTO,employee);
        //设置用户信息修改时间
        employee.setUpdateTime(LocalDateTime.now());
        //获取修改员工信息账号ID
        Long id = BaseContext.getCurrentId();
        log.info("操作人ID：{}",id);
        employee.setUpdateUser(id);
        //调用Mapper方法进行数据库数据的修改
        employeeMapper.updateEmp(employee);
    }
}
