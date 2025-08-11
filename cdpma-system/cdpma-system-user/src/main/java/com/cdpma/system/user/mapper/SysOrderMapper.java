package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.dto.OrderResponseDTO;
import com.cdpma.common.pojo.pojo.SysOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOrderMapper {

    /** 插入订单 */
    int insert(SysOrder order);

    /** 根据ID删除 */
    int deleteById(Long orderId);

    /** 根据ID更新 */
    int updateById(SysOrder order);

    /** 根据ID查询 */
    SysOrder selectById(Long orderId);

    /** 查询所有订单 */
    List<SysOrder> selectAll();

    List<OrderResponseDTO> userSearchOrders(@Param("goodName") String goodName, @Param("isPaid") Boolean isPaid
            , @Param("isCancelled") Boolean isCancelled, @Param("operatorId") Long operatorId);
}
