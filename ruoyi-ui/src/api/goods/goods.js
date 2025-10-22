import request from '@/utils/request'

// 查询货源信息列表
export function listGoods(query) {
  return request({
    url: '/goods/goods/list',
    method: 'get',
    params: query
  })
}

export function listArrivalGoods(query) {
  return request({
    url: '/goods/goods/arrival/list',
    method: 'get',
    params: query
  })
}

// 查询货源信息详细
export function getGoods(id) {
  return request({
    url: '/goods/goods/' + id,
    method: 'get'
  })
}

// 新增货源信息
export function addGoods(data) {
  return request({
    url: '/goods/goods',
    method: 'post',
    data: data
  })
}

// 修改货源信息
export function updateGoods(data) {
  return request({
    url: '/goods/goods',
    method: 'put',
    data: data
  })
}

export function arrvialEdit(data) {
  return request({
    url: '/goods/goods/arrivalEdit',
    method: 'post',
    data: data
  })
}

export function publishGoods(data) {
  return request({
    url: '/goods/goods/publish',
    method: 'post',
    data: data
  })
}

// 删除货源信息
export function delGoods(id) {
  return request({
    url: '/goods/goods/' + id,
    method: 'delete'
  })
}
