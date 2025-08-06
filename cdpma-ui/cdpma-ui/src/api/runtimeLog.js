import request from '@/utils/request'

export function fetchRuntimeLogs(params) {
  return request({
    url: `/system-user/log-runtime/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}`,
    method: 'post',
    data: {
        "status": params.status,
        "operatorId": params.operatorId,
    }
  })
}
