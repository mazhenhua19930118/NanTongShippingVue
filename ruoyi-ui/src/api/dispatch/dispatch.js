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

// 查询本单派送人员
export function getUserByDispatchGoods (data) {
  return request({
    url: '/dispatch/dispatch/getUserByDispatchGoods',
    method: 'post',
    data: data
  })
}

export function selectNqPeople () {
  return request({
    url: '/system/post/selectNqPeople',
    method: 'get'
  })
}

// 派单
export function dispatchToUser (data) {
  return request({
    url: '/dispatch/dispatch/dispatchToUser',
    method: 'post',
    data: data
  })
}
