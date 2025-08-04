import request from '@/utils/request'

export function getOperatorList(queryParams) {
  return request({
    url: '/system-user/operator/selectList',
    method: 'post',
    data: {
      operatorName: queryParams.operatorName,
      operatorPhone: queryParams.operatorPhone,
      operatorEmail: queryParams.operatorEmail,
      operatorRole: queryParams.operatorRole
    }
  })
}