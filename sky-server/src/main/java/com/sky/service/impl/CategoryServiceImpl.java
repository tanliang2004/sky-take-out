package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @ClassName CategoryServiceImpl
 * @Author t1533
 * @Date 2025/3/5 15:58
 * @Description
 * @Version 1.0.0
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //菜品类
    @Autowired
    private DishMapper dishMapper;

    //套餐类
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 菜品分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        //使用PageHelper插件进行分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.selectAll(categoryPageQueryDTO);
        int pages = page.getPages();
        int pageSize = page.getPageSize();
        log.info("菜品分类分页：{},{}", pages, pageSize);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }

    /**
     * 菜品分类启用禁用
     *
     * @param status
     * @param id
     */
    public void startOrShut(Integer status, Long id) {
        //创建Category对象,将status和id值传入。使用动态SQL语句实现
        Category category = Category.builder().id(id).status(status).build();
        categoryMapper.updateCategory(category);
    }

    /**
     * 新增分类
     *
     * @param categoryDTO
     */
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        //设置分类创建时间和修改时间(使用AOP公共字段自动填充)
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
        //获取当前登录账号ID,设置创建人ID和修改人ID
//        Long id = BaseContext.getCurrentId();
//        category.setCreateUser(id);
//        category.setUpdateUser(id);
        //设置分类的初始化状态为（禁用0）
        category.setStatus(0);
        categoryMapper.insertCategory(category);
    }

    /**
     * 根据ID删除分类
     *
     * @param id
     */
    // TODO 当第二页只有一个类别时，将其删除，页面查询没有任何数据（待处理）
    @Transactional
    public void removeCategory(Long id) {
        //需要查询当前类别下是否还有菜品，如果没有才能删除
        Long countDish = dishMapper.countByCategoryId(id);
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        if (countDish > 0) throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        Long countSetmeal = setmealMapper.countByCategoryId(id);
        if (countSetmeal > 0) throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);

        //判断结束，进行删除
        categoryMapper.deleteCategory(id);
    }

    /**
     * 根据类型查询
     *
     * @param type
     * @return
     */
    public Category getListType(Integer type) {
        return categoryMapper.selectType(type);
    }

    /**
     * 修改菜品分类
     *
     * @param categoryDTO
     */
    public void edit(CategoryDTO categoryDTO) {
        //对象拷贝到Category对象中
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        //修改最后操作时间和最后操作用户(使用AOP公共字段自动填充)
//        Long id = BaseContext.getCurrentId();
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(id);
        //调用Mapper方法
        categoryMapper.updateCategory(category);
    }
}
