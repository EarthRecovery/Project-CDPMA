import request from '@/utils/request'

export function getNotificationList(params){
    return request({
        url: `system-admin/notification/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}`,
        method: 'post',
        data: params
    })
}
