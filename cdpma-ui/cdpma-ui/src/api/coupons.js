import request from '@/utils/request'

export function getCouponsList(queryParams) {
    return request({
        url: `/system-user/coupons/userSearch/${queryParams.isExpired}/${queryParams.operatorId}?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
        method: 'get',
    })
}