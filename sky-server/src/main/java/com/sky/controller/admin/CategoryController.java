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
@Api("分类管理")
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
}
