import request from '@/utils/request'

export function addLikeAPI(operatorId, goodId) {
    return request({
        url: `/system-user/like`,
        method: 'post',
        data: {
            "operatorId": operatorId,
            "goodId": goodId
        }
    })
}

export function deleteLikeAPI(operatorId, goodId) {
    return request({
        url: `/system-user/like`,
        method: 'delete',
        data: {
            "operatorId": operatorId,
            "goodId": goodId
        }
    })
}
