import request from '@/utils/request'

export function getFavoriteList(operatorId, query) {
    return request({
        url: `/system-user/favorites/operator/${operatorId}?pageNum=${query.pageNum}&pageSize=${query.pageSize}`,
        method: 'get',
    })
}

export function deleteFavoriteByIds(favoriteIds) {
    return request({
        url: `/system-user/favorites/${favoriteIds}`,
        method: 'delete'
    })
}
