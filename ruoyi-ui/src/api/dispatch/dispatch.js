import request from '@/utils/request'

// 查询调度管理列表
export function listDispatch(query) {
  return request({
    url: '/dispatch/dispatch/list',
    method: 'get',
    params: query
  })
}

// 查询调度管理详细
export function getDispatch(id) {
  return request({
    url: '/dispatch/dispatch/' + id,
    method: 'get'
  })
}

// 新增调度管理
export function addDispatch(data) {
  return request({
    url: '/dispatch/dispatch',
    method: 'post',
    data: data
  })
}

// 修改调度管理
export function updateDispatch(data) {
  return request({
    url: '/dispatch/dispatch',
    method: 'put',
    data: data
  })
}

// 删除调度管理
export function delDispatch(id) {
  return request({
    url: '/dispatch/dispatch/' + id,
    method: 'delete'
  })
}
