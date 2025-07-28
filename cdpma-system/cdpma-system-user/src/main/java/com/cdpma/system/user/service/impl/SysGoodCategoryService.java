package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysGoodCategory;
import com.cdpma.system.user.mapper.SysGoodCategoryMapper;
import com.cdpma.system.user.service.ISysGoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysGoodCategoryService implements ISysGoodCategoryService {

    @Autowired
    private SysGoodCategoryMapper categoryMapper;

    @Override
    public SysGoodCategory selectCategoryById(Long categoryId) {
        return categoryMapper.selectCategoryById(categoryId);
    }

    @Override
    public List<SysGoodCategory> selectCategoryList(SysGoodCategory category) {
        return categoryMapper.selectCategoryList(category);
    }

    @Override
    public int insertCategory(SysGoodCategory category) {
        return categoryMapper.insertCategory(category);
    }

    @Override
    public int updateCategory(SysGoodCategory category) {
        return categoryMapper.updateCategory(category);
    }

    @Override
    public int deleteCategoryByIds(Long[] categoryIds) {
        return categoryMapper.deleteCategoryByIds(categoryIds);
    }
}
