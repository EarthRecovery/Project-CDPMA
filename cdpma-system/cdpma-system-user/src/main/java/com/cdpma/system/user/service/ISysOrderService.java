package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysOrder;

import java.util.List;

public interface ISysOrderService {

    int addOrder(SysOrder order);

    int deleteOrder(Long orderId);

    int updateOrder(SysOrder order);

    SysOrder getOrderById(Long orderId);

    List<SysOrder> getAllOrders();
}
