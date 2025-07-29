package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.system.user.service.ISysOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class SysOrderController extends BaseController {

    @Resource
    private ISysOrderService sysOrderService;

    @PostMapping
    public AjaxResult addOrder(@RequestBody SysOrder order) {
        sysOrderService.addOrder(order);
        return AjaxResult.success("增加订单成功");
    }

    @DeleteMapping("/{orderId}")
    public AjaxResult deleteOrder(@PathVariable Long orderId) {
        sysOrderService.deleteOrder(orderId);
        return AjaxResult.success("删除订单成功");
    }

    @PutMapping
    public AjaxResult updateOrder(@RequestBody SysOrder order) {
        sysOrderService.updateOrder(order);
        return AjaxResult.success("更新订单成功");
    }

    @GetMapping("/{orderId}")
    public AjaxResult getOrderById(@PathVariable Long orderId) {
        SysOrder sysOrder = sysOrderService.getOrderById(orderId);
        return AjaxResult.success(sysOrder);
    }

    @GetMapping
    public AjaxResult getAllOrders() {
        List<SysOrder> orders = sysOrderService.getAllOrders();
        return AjaxResult.success(orders);
    }
}
