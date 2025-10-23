import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listTask (query) {
  return request({
    url: '/documentation/task/list',
    method: 'get',
    params: query
  })
}

export function listCameraTask (query) {
  return request({
    url: '/documentation/task/camera/list',
    method: 'get',
    params: query
  })
}

export function listUnloadTask (query) {
  return request({
    url: '/documentation/task/unload/list',
    method: 'get',
    params: query
  })
}

export function listInternalTask (query) {
  return request({
    url: '/documentation/task/internal/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getTask (id) {
  return request({
    url: '/documentation/task/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addTask (data) {
  return request({
    url: '/documentation/task',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateTask (data) {
  return request({
    url: '/documentation/task',
    method: 'post',
    data: data,
    headers: { 'Content-Type': ' multipart/form-data'}
    })
}

export function updateCameraTask (data) {
  return request({
    url: '/documentation/task/camera',
    method: 'post',
    data: data,
    headers: { 'Content-Type': ' multipart/form-data'}
  })
}

export function updateUnloadTask (data) {
  return request({
    url: '/documentation/task/unload',
    method: 'post',
    data: data,
    headers: { 'Content-Type': ' multipart/form-data'}
  })
}

// 删除【请填写功能名称】
export function delTask (id) {
  return request({
    url: '/documentation/task/' + id,
    method: 'delete'
  })
}

//查询所有任务
export function comprehensive (query) {
  return request({
    url: '/documentation/task/comprehensive',
    method: 'get',
    params: query
  })
}

//查询任务节点
export function taskNode (query) {
  return request({
    url: '/documentation/task/taskNode',
    method: 'get',
    params: query
  })
}

//查询任务详情
export function taskDetail (query) {
  return request({
    url: '/documentation/taskDetail/detail',
    method: 'get',
    params: query
  })
}

//查询所有任务节点（终止）
export function getTaskNodeList (query) {
  return request({
    url: '/documentation/task/getTaskNodeList',
    method: 'get',
    params: query
  })
}


export function getTaskWithImage (data) {
  return request({
    url: '/documentation/task/getTaskWithImage',
    method: 'post',
    params: data
  })
}

//综合查询明细查询
export function getGoodsDetails (query) {
  return request({
    url: '/documentation/task/getGoodsDetails',
    method: 'post',
    params: query
  })
}




