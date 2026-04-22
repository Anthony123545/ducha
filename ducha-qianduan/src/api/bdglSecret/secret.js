import request from '@/utils/request'

// 查询保密设备外借记录列表
export function listSecret(query) {
  return request({
    url: '/bdglSecret/secret/list',
    method: 'get',
    params: query
  })
}

// 查询保密设备外借记录详细
export function getSecret(id) {
  return request({
    url: '/bdglSecret/secret/' + id,
    method: 'get'
  })
}

// 查询统计详细
export function getNumber(query) {
  return request({
    url: '/bdglSecret/secret/getNumber',
    method: 'get',
    params: query
  })
}

// 新增保密设备外借记录
export function addSecret(data) {
  return request({
    url: '/bdglSecret/secret',
    method: 'post',
    data: data
  })
}

// 修改保密设备外借记录
export function updateSecret(data) {
  return request({
    url: '/bdglSecret/secret',
    method: 'put',
    data: data
  })
}

export function updateStatus(data) {
  return request({
    url: '/bdglSecret/secret/updateStatus',
    method: 'post',
    data: data
  });
}

// 删除保密设备外借记录
export function delSecret(id) {
  return request({
    url: '/bdglSecret/secret/' + id,
    method: 'delete'
  })
}

// 导出保密设备外借记录
export function exportSecret(query) {
  return request({
    url: '/bdglSecret/secret/export',
    method: 'get',
    params: query
  })
}

// 查询部门
export function getDepts(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}
