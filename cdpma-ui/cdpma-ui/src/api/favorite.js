import request from '@/utils/request'

export function getFavoriteList(operatorId, query) {
    return request({
        url: `/system-user/favorites/operator/${operatorId}?pageNum=${query.pageNum}&pageSize=${query.pageSize}`,
        method: 'get',
    })
}

export function deleteFavoriteByIds(operatorId, goods) {
    return request({
        url: `/system-user/favorites/delete/${operatorId}`,
        method: 'post',
        data: goods
    })
}
