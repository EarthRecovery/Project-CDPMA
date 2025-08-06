import request from '@/utils/request'

export function getXXList(query) {
    return request({
        url: `/system-user/XX/search?pageNum=${query.pageNum}&pageSize=${query.pageSize}`,
        method: 'post',
        data: query
    })
}

export function editXX(data) {
    return request({
        url: '/system-user/XX',
        method: 'put',
        data: data
    })
}

export function deleteXXByIds(XXIds) {
    return request({
        url: `/system-user/XX/${XXIds}`,
        method: 'delete'
    })
}

export function createXX(data) {
    return request({
        url: '/system-user/XX',
        method: 'post',
        data: data
    })
}