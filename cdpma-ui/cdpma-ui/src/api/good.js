import request from '@/utils/request'

export function getCategory() {
    return request({
        url: '/system-user/good-category/list',
        method: 'get'
    })
}

export function getGoodList(query) {
    return request({
        url: `/system-user/goods/search?pageNum=${query.pageNum}&pageSize=${query.pageSize}`,
        method: 'post',
        data: query
    })
}

export function getCategoryName(categoryId) {
    return request({
        url: `/system-user/good-category/${categoryId}`,
        method: 'get'
    })
}

export function editGood(data) {
    return request({
        url: '/system-user/goods',
        method: 'put',
        data: data
    })
}

export function deleteGoodByIds(goodIds) {
    return request({
        url: `/system-user/goods/${goodIds}`,
        method: 'delete'
    })
}

export function createGood(data) {
    return request({
        url: '/system-user/goods',
        method: 'post',
        data: data
    })
}

export function likeGood(goodId, operatorId) {
    return request({
        url: `/system-user/like`,
        method: 'post',
        data: { 
            "operatorId": operatorId,
            "goodId": goodId
        }
    })
}

export function favoriteGood(goodId, operatorId) {
    return request({
        url: `/system-user/favorites`,
        method: 'post',
        data: { 
            "operatorId": operatorId,
            "goodId": goodId
        }
    })
}

export function buyGood(form, operatorId){
    return request({
        url: '/system-user/order',
        method: 'post',
        data: {
            "operatorId": operatorId,
            "goodId": form.goodId,
            "quantity": form.quantity,
            "unitPrice": form.price,
            "isPaid": false,
            "isCancelled": false,
            "feedback": null,
        }
    })
}