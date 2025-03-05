package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
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
}
