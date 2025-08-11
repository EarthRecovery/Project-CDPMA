import request from '@/utils/request'

export function getOrderList(queryParams) {
    return request({
        url: `/system-user/order/userSearch/${queryParams.goodName}/${queryParams.isPaid}/${queryParams.isCancelled}/${queryParams.operatorId}?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
        method: 'get',
    })
}

export function payOrder(orderId) {
    return request({
        url: `/system-user/order/pay/${orderId}`,
        method: 'get'
    })
}

export function cancelOrder(orderId) {
    return request({
        url: `/system-user/order/cancel/${orderId}`,
        method: 'get'
    })
}

export function editScore(orderId, score) {
    return request({
        url: `/system-user/order/score/${orderId}/${score}`,
        method: 'get'
    })
}

export function editFeedBack(orderId, feedback) {
    return request({
        url: `/system-user/order/feedback/${orderId}/${feedback}`,
        method: 'get'
    })
}