package com.cdpma.common.core.utils;

import com.cdpma.common.core.web.page.PageDomain;
import com.cdpma.common.core.web.page.TableSupport;
import com.github.pagehelper.PageHelper;

public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        System.out.println("PageUtils startPage: pageNum=" + pageNum + ", pageSize=" + pageSize);
        String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
