import request from '@/utils/request'

// 查询摄像头管理列表
export function listBrigadecamera(query) {
  return request({
    url: '/monitoring/brigadecamera/list',
    method: 'get',
    params: query
  })
}

// 查询摄像头管理详细
export function getBrigadecamera(id) {
  return request({
    url: '/monitoring/brigadecamera/' + id,
    method: 'get'
  })
}

// 新增摄像头管理
export function addBrigadecamera(data) {
  return request({
    url: '/monitoring/brigadecamera',
    method: 'post',
    data: data
  })
}

// 修改摄像头管理
export function updateBrigadecamera(data) {
  return request({
    url: '/monitoring/brigadecamera',
    method: 'put',
    data: data
  })
}

// 删除摄像头管理
export function delBrigadecamera(id) {
  return request({
    url: '/monitoring/brigadecamera/' + id,
    method: 'delete'
  })
}
