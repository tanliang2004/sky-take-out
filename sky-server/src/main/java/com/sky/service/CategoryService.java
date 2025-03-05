package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

public interface CategoryService {

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 菜品分类启用禁用
     * @param status
     * @param id
     */
    void startOrShut(Integer status, Long id);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 根据ID删除分类
     * @param id
     */
    void removeCategory(Long id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    Category getListType(Integer type);

    /**
     * 修改菜品分类
     * @param categoryDTO
     */
    void edit(CategoryDTO categoryDTO);
}
