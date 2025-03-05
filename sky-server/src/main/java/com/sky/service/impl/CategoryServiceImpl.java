package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        //使用PageHelper插件进行分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.selectAll(categoryPageQueryDTO);
        int pages = page.getPages();
        int pageSize = page.getPageSize();
        log.info("菜品分类分页：{},{}",pages,pageSize);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }

    /**
     * 菜品分类启用禁用
     * @param status
     * @param id
     */
    @Override
    public void startOrShut(Integer status, Long id) {
        //创建Category对象,将status和id值传入。使用动态SQL语句实现
        Category category = Category.builder().id(id).status(status).build();
        categoryMapper.updateCategory(category);

    }
}
