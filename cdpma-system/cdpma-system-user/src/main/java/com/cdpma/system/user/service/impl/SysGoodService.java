package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.dto.GoodQueryDTO;
import com.cdpma.common.pojo.pojo.SysGood;
import com.cdpma.common.pojo.pojo.SysGoodCategory;
import com.cdpma.common.pojo.pojo.SysGoodFullName;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.mapper.SysGoodMapper;
import com.cdpma.system.user.service.ISysGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SysGoodService implements ISysGoodService {

    @Autowired
    private SysGoodMapper goodMapper;

    @Autowired
    private SysGoodCategoryService goodCategoryService;

    @Override
    public SysGood selectGoodById(Long goodId) {
        return goodMapper.selectGoodById(goodId);
    }

    @Override
    public List<SysGood> selectGoodList(SysGood good) {
        return goodMapper.selectGoodList(good);
    }

    @Override
    public int insertGood(SysGood good) {
        good.setCreatedAt(new Date());
        good.setCreatedBy(SecurityUtils.getOperatorId());
        good.setDisabled(false);
        return goodMapper.insertGood(good);
    }

    @Override
    public int updateGood(SysGood good) {
        good.setUpdatedAt(new Date());
        good.setUpdatedBy(SecurityUtils.getOperatorId());
        return goodMapper.updateGood(good);
    }

    @Override
    public int deleteGoodByIds(Long[] goodIds) {
        return goodMapper.deleteGoodByIds(goodIds);
    }

    @Override
    public SysGoodFullName getFullNameGood(Long goodId) {
        SysGood good = goodMapper.selectGoodById(goodId);
        Long categoryId = good.getCategoryId();
        SysGoodCategory category = goodCategoryService.selectCategoryById(categoryId);
        SysGoodFullName fullName = new SysGoodFullName();
        fullName.setGoodId(good.getGoodId());
        fullName.setGoodName(good.getGoodName());
        fullName.setCategoryId(category.getCategoryId());
        fullName.setCategoryName(category.getCategoryName());
        fullName.setCreatedAt(good.getCreatedAt());
        fullName.setUpdatedAt(good.getUpdatedAt());
        fullName.setCreatedBy(good.getCreatedBy());
        fullName.setUpdatedBy(good.getUpdatedBy());
        fullName.setPrice(good.getPrice());
        fullName.setDisabled(good.getDisabled());
        if(category.getParentCategory() != -1){
            fullName.setFatherCategoryName(goodCategoryService.selectCategoryById(category.getParentCategory()).getCategoryName());
        }else{
            fullName.setFatherCategoryName("Null");
        }
        return fullName;
    }

    @Override
    public List<SysGood> searchGoods(GoodQueryDTO queryDTO) {
        if(queryDTO.getPriceHigh() == null){
            queryDTO.setPriceHigh(BigDecimal.valueOf(Double.MAX_VALUE));
        }
        if(queryDTO.getPriceLow() == null){
            queryDTO.setPriceLow(BigDecimal.ZERO);
        }
        return goodMapper.searchGoods(queryDTO);
    }

    @Override
    public int editGoodLike(Long goodId, int likeDelta) {
        return goodMapper.editGoodLike(goodId, likeDelta);
    }
}
