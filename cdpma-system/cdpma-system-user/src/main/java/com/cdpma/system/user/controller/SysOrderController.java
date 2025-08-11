package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.core.web.page.TableDataInfo;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.enums.BusinessType;
import com.cdpma.common.pojo.dto.OrderResponseDTO;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.common.security.annotation.Logical;
import com.cdpma.common.security.annotation.RequiresTags;
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
    @Log(title = "新增订单", businessType = BusinessType.INSERT)
    public AjaxResult addOrder(@RequestBody SysOrder order) {
        sysOrderService.addOrder(order);
        return AjaxResult.success("增加订单成功");
    }

    @DeleteMapping("/{orderId}")
    @RequiresTags(value= {Tag.ADMIN, Tag.ASSISTANT}, logical = Logical.OR)
    @Log(title = "删除订单", businessType = BusinessType.DELETE)
    public AjaxResult deleteOrder(@PathVariable Long orderId) {
        sysOrderService.deleteOrder(orderId);
        return AjaxResult.success("删除订单成功");
    }

    @PutMapping
    @RequiresTags(value= {Tag.ADMIN, Tag.ASSISTANT}, logical = Logical.OR)
    @Log(title = "更新订单", businessType = BusinessType.UPDATE)
    public AjaxResult updateOrder(@RequestBody SysOrder order) {
        sysOrderService.updateOrder(order);
        return AjaxResult.success("更新订单成功");
    }

    @GetMapping("/{orderId}")
    @RequiresTags(value = {Tag.ADMIN, Tag.ASSISTANT}, logical = Logical.OR)
    public AjaxResult getOrderById(@PathVariable Long orderId) {
        SysOrder sysOrder = sysOrderService.getOrderById(orderId);
        return AjaxResult.success(sysOrder);
    }

    @GetMapping("/userSearch/{goodName}/{isPaid}/{isCancelled}/{operatorId}")
    public TableDataInfo userSearchOrders(@PathVariable String goodName,
                                          @PathVariable Boolean isPaid,
                                          @PathVariable Boolean isCancelled,
                                          @PathVariable Long operatorId) {
        startPage();
        List<OrderResponseDTO> orders = sysOrderService.userSearchOrders(goodName, isPaid, isCancelled, operatorId);
        return getDataTable(orders);
    }

    @GetMapping("/pay/{orderId}")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    public AjaxResult payOrder(@PathVariable Long orderId) {
        SysOrder order = sysOrderService.getOrderById(orderId);
        if (order == null || order.getPaid() == true) {
            return AjaxResult.error("订单不存在或已支付");
        }
        order.setPaid(true);
        sysOrderService.updateOrder(order);
        return AjaxResult.success("订单支付成功");
    }

    @GetMapping("/cancel/{orderId}")
    @Log(title = "取消订单", businessType = BusinessType.UPDATE)
    public AjaxResult cancelOrder(@PathVariable Long orderId) {
        SysOrder order = sysOrderService.getOrderById(orderId);
        if (order == null || order.getCancelled() == true) {
            return AjaxResult.error("订单不存在或已取消");
        }
        order.setCancelled(true);
        sysOrderService.updateOrder(order);
        return AjaxResult.success("订单取消成功");
    }

    @GetMapping("/score/{orderId}/{score}")
    @Log(title = "编辑订单评分", businessType = BusinessType.UPDATE)
    public AjaxResult editOrderScore(@PathVariable Long orderId, @PathVariable Integer score) {
        SysOrder order = sysOrderService.getOrderById(orderId);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        order.setFeedbackScore(score);
        sysOrderService.updateOrder(order);
        return AjaxResult.success("订单评分更新成功");
    }

    @GetMapping("/feedback/{orderId}/{feedback}")
    @Log(title = "编辑订单反馈", businessType = BusinessType.UPDATE)
    public AjaxResult editOrderFeedback(@PathVariable Long orderId, @PathVariable String feedback) {
        SysOrder order = sysOrderService.getOrderById(orderId);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        order.setFeedback(feedback);
        sysOrderService.updateOrder(order);
        return AjaxResult.success("订单反馈更新成功");
    }

    @GetMapping
    @RequiresTags(value = {Tag.ADMIN, Tag.ASSISTANT}, logical = Logical.OR)
    public AjaxResult getAllOrders() {
        List<SysOrder> orders = sysOrderService.getAllOrders();
        return AjaxResult.success(orders);
    }
}
