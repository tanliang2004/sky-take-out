package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> selectAll(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 菜品分类动态SQL修改信息
     * @param category
     */
    void updateCategory(Category category);
}
