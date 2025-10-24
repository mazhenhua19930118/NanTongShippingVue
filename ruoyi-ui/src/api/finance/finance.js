import request from '@/utils/request'

// 查询财务管理列表
export function listFinance(query) {
  return request({
    url: '/finance/finance/list',
    method: 'get',
    params: query
  })
}

// 查询财务管理详细
export function getFinance(id) {
  return request({
    url: '/finance/finance/' + id,
    method: 'get'
  })
}

// 新增财务管理
export function addFinance(data) {
  return request({
    url: '/finance/finance',
    method: 'post',
    data: data
  })
}

// 修改财务管理
export function updateFinance(data) {
  return request({
    url: '/finance/finance',
    method: 'put',
    data: data
  })
}

export function updateSettlement(data) {
  return request({
    url: '/finance/finance/settlement',
    method: 'post',
    data: data
  })
}

export function reportUnloadingData(data) {
  return request({
    url: '/finance/finance/reportUnloadingData',
    method: 'post',
    data: data
  })
}

// 删除财务管理
export function delFinance(id) {
  return request({
    url: '/finance/finance/' + id,
    method: 'delete'
  })
}
