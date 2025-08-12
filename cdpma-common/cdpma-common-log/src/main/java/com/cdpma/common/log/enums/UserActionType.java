package com.cdpma.common.log.enums;

public enum UserActionType {
    /**
     * 用户点赞
     */
    LIKERECORD_ADD,


    /**
     * 用户收藏
     */
    FAVORITE_ADD,

    /**
     * 下订单
     */
    ORDER_ADD,

    /**
     * 订单评分
     */
    ORDER_SCORE,

    /**
     * 订单评论
     */
    ORDER_COMMENT,

    /**
     * 订单付款
     */
    ORDER_PAY,


    /**
     * 用户取消订单
     */
    ORDER_CANCEL,

    /**
     * 默认
     */
    DEFAULT,


    /**
     * 用户点赞取消
     */
    LIKERECORD_DELETE,
}
