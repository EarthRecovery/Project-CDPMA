import request from '@/utils/request'

export function getOperationTriggerList() {
  return request({
    url: '/system-admin/operation-trigger-condition/all',
    method: 'get',
  })
}

export function addOperationTrigger(data) {
  return request({
    url: '/system-admin/operation-trigger-condition',
    method: 'post',
    data: data,
  })
}

export function addOperation(data) {
  return request({
    url: '/system-admin/operation',
    method: 'post',
    data: data,
  })
}

export function editOperation(data) {
  return request({
    url: `/system-admin/operation`,
    method: 'put',
    data: data,
  })
}

export function getOperationResponseList() {
  return request({
    url: '/system-admin/operation-response/all',
    method: 'get',
  })
}


export function getOperationList(queryParams) {
  return request({
    url: `/system-admin/operation/list?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
    method: 'post',
    data: queryParams,
  })
}