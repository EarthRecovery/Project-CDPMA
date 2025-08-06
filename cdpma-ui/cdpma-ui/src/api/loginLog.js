import request from '@/utils/request'

export function getLoginLogList(params) {
  return request({
    url: `/system-user/logio/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}`,
    method: 'post',
    data: {
        "operatorId": params.operatorId,
        "action": params.action,
    }
  })
}
