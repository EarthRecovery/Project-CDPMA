package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysGoodCategory;

import java.util.List;

public interface ISysGoodCategoryService {
    SysGoodCategory selectCategoryById(Long categoryId);

    List<SysGoodCategory> selectCategoryList(SysGoodCategory category);

    int insertCategory(SysGoodCategory category);

    int updateCategory(SysGoodCategory category);

    int deleteCategoryByIds(Long[] categoryIds);
}
