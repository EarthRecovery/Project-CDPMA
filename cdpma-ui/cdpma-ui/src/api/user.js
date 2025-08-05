import request from '@/utils/request'

export function getUserByOperatorID(operatorId) {
  return request({
    url: `/system-user/user/operator/${operatorId}`,
    method: 'get'
  })
}