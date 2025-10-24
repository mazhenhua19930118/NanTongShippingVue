import request from '@/utils/request'

// 查询保险管理列表
export function listInsurance(query) {
  return request({
    url: '/insurance/insurance/list',
    method: 'get',
    params: query
  })
}

export function listSureInsurance(query) {
  return request({
    url: '/insurance/insurance/sure/list',
    method: 'get',
    params: query
  })
}


// 查询保险管理详细
export function getInsurance(id) {
  return request({
    url: '/insurance/insurance/' + id,
    method: 'get'
  })
}

// 新增保险管理
export function addInsurance(data) {
  return request({
    url: '/insurance/insurance',
    method: 'post',
    data: data
  })
}

// 修改保险管理
export function updateInsurance(data) {
  return request({
    url: '/insurance/insurance',
    method: 'put',
    data: data
  })
}

// 删除保险管理
export function delInsurance(id) {
  return request({
    url: '/insurance/insurance/' + id,
    method: 'delete'
  })
}
