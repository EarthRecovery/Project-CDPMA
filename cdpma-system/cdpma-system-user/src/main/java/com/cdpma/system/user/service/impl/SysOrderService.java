package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.mapper.SysOrderMapper;
import com.cdpma.system.user.service.ISysOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysOrderService implements ISysOrderService {

    @Resource
    private SysOrderMapper sysOrderMapper;

    @Override
    public int addOrder(SysOrder order) {
        order.setOrderTime(new Date());
        order.setCancelled(false);
        order.setOperatorId(SecurityUtils.getOperatorId());
        return sysOrderMapper.insert(order);
    }

    @Override
    public int deleteOrder(Long orderId) {
        return sysOrderMapper.deleteById(orderId);
    }

    @Override
    public int updateOrder(SysOrder order) {
        return sysOrderMapper.updateById(order);
    }

    @Override
    public SysOrder getOrderById(Long orderId) {
        return sysOrderMapper.selectById(orderId);
    }

    @Override
    public List<SysOrder> getAllOrders() {
        return sysOrderMapper.selectAll();
    }
}
