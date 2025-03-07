package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 菜品分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> selectAll(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 菜品分类动态SQL修改信息
     *
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);

    /**
     * 新增分类
     *
     * @param category
     */
    @AutoFill(value = OperationType.INSERT)
    void insertCategory(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    Category selectType(Integer type);
}
