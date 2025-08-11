import request from '@/utils/request'

export function operatorMapStat(){
    return request({
        url: `/stat/userstat/map`,
        method: 'get'
    })
}