import request from '@/utils/request'

export function operatorMapStat(){
    return request({
        url: `/stat/userstat/map`,
        method: 'get'
    })
}

export function refresh(){
    return request({
        url: `/stat/userstat/refresh`,
        method: 'get'
    })
}

