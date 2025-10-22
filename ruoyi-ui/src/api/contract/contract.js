import request from '@/utils/request'

// 查询合同管理列表
export function listContract(query) {
  return request({
    url: '/contract/contract/list',
    method: 'get',
    params: query
  })
}

export function listMainContract(query) {
  return request({
    url: '/contract/contract/mcdainList',
    method: 'get',
    params: query
  })
}

export function listSubContract(query) {
  return request({
    url: '/contract/contract/subList',
    method: 'get',
    params: query
  })
}

// 查询合同管理详细
export function getContract(id) {
  return request({
    url: '/contract/contract/' + id,
    method: 'get'
  })
}

// 新增合同管理
export function addContract(data) {
  return request({
    url: '/contract/contract',
    method: 'post',
    data: data
  })
}

// 修改合同管理
export function updateContract(data) {
  return request({
    url: '/contract/contract',
    method: 'put',
    data: data
  })
}

export function updateMainContract(data) {
  return request({
    url: '/contract/contract/mainSign',
    method: 'post',
    data: data
  })
}

export function updateSubContract(data) {
  return request({
    url: '/contract/contract/subSign',
    method: 'post',
    data: data
  })
}

// 删除合同管理
export function delContract(id) {
  return request({
    url: '/contract/contract/' + id,
    method: 'delete'
  })
}
