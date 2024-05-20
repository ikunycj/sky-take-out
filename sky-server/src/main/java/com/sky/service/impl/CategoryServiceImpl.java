package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类对象
     * @param categoryDTO
     */
    public void addCategory(CategoryDTO categoryDTO) {
        //创建分类对象
        Category category = new Category();

        //拷贝对象属性
        BeanUtils.copyProperties(categoryDTO, category);

        //设置分类状态 0标识禁用 1表示启用
        category.setStatus(StatusConstant.ENABLE);

       //    创建时间
       //category.setCreateTime(LocalDateTime.now());

       //    更新时间
       //category.setUpdateTime(LocalDateTime.now());


       //    创建人
       //category.setCreateUser(BaseContext.getCurrentId());

       //    修改人
       //category.setUpdateUser(BaseContext.getCurrentId());

        //插入数据库表格
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> page =categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> results = page.getResult();

        return new PageResult(total,results);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO,category);

       //category.setUpdateTime(LocalDateTime.now());
       //category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }


    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }


    /**
     * 启用禁用分类
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();

        categoryMapper.update(category);
    }


    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    // TODO 自己写的需要完善返回值
    public List<Category> list(Integer type) {
        //类型: 1菜品分类 2套餐分类
        return categoryMapper.list(type);
    }
}
