import request from '@/utils/request'

// 查询审批列表
export function listApprove(query) {
  return request({
    url: '/approve/approve/list',
    method: 'get',
    params: query
  })
}

// 查询审批详细
export function getApprove(id) {
  return request({
    url: '/approve/approve/' + id,
    method: 'get'
  })
}

// 新增审批
export function addApprove(data) {
  return request({
    url: '/approve/approve',
    method: 'post',
    data: data
  })
}

// 修改审批
export function updateApprove(data) {
  return request({
    url: '/approve/approve',
    method: 'put',
    data: data
  })
}

// 删除审批
export function delApprove(id) {
  return request({
    url: '/approve/approve/' + id,
    method: 'delete'
  })
}
