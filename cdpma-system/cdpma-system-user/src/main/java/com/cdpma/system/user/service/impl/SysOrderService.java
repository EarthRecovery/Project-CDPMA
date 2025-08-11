package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.dto.OrderResponseDTO;
import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.mapper.SysOrderMapper;
import com.cdpma.system.user.service.ISysOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<OrderResponseDTO> userSearchOrders(String goodName, Boolean isPaid, Boolean isCancelled, Long operatorId) {
        if(Objects.equals(goodName, "null")) goodName = null;
        return sysOrderMapper.userSearchOrders(goodName, isPaid, isCancelled,operatorId);
    }
}
