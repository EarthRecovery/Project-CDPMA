import request from '@/utils/request'

export function getOperatorList(queryParams) {
  return request({
    url: `/system-user/operator/selectList?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
    method: 'post',
    data: {
      operatorName: queryParams.operatorName,
      operatorPhone: queryParams.operatorPhone,
      operatorEmail: queryParams.operatorEmail,
      operatorRole: queryParams.operatorRole
    }
  })
}

export function deleteOperatorById(operatorId) {
  return request({
    url: `/system-user/operator/single/${operatorId}`,
    method: 'delete'
  })
}

export function deleteOperatorByIds(operatorIds) {
  return request({
    url: `/system-user/operator/batch/${operatorIds}`,
    method: 'delete'
  })
}

export function addOperator(operator) {
  return request({
    url: '/system-user/operator',
    method: 'post',
    data: operator
  })
}

export function editOperator(operator) {
  return request({
    url: '/system-user/operator',
    method: 'put',
    data: operator
  })
}