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

export function timeCostStat(){
    return request({
        url: `/stat/logstat/timeCost`,
        method: 'get'
    })
}

export function timeCostStatRefresh(){
    return request({
        url: `/stat/logstat/refresh`,
        method: 'get'
    })
}

