package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CategoryController
 * @Author t1533
 * @Date 2025/3/5 15:44
 * @Description
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(value = "分类管理")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("菜品分类分页查询：{}",categoryPageQueryDTO);
        PageResult page = categoryService.page(categoryPageQueryDTO);
        return Result.success(page);
    }


    /**
     * 菜品分类启用禁用
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("分类启用禁用")
    public Result startOrShut(@PathVariable Integer status,Long id) {
        log.info("分类启用禁用ID：{}",id);
        categoryService.startOrShut(status,id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 根据ID删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result removeCategory(Long id) {
        categoryService.removeCategory(id);
        return Result.success();
    }

    /**
     * 修改菜品分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改菜品分类")
    public Result edit(@RequestBody CategoryDTO categoryDTO) {
        log.info("修稿菜品分类数据：{}",categoryDTO);
        categoryService.edit(categoryDTO);
        return Result.success();
    }

    /**
     * 根据类型查询
     * @param type 1为菜品分类,2为类型分类
     * @return
     */
    // TODO 功能业务存在问题
//    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询")
    public Result<Category> getListType(Integer type) {
        Category category = categoryService.getListType(type);
        return Result.success(category);
    }
}
