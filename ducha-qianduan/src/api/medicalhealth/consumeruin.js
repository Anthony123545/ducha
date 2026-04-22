import request from '@/utils/request'

// 查询耗材销毁记录列表
export function listRuin(query) {
  return request({
    url: '/medicalhealth/consumeruin/list',
    method: 'get',
    params: query
  })
}

// 查询耗材销毁记录详细
export function getRuin(id) {
  return request({
    url: '/medicalhealth/consumeruin/' + id,
    method: 'get'
  })
}

// 新增耗材销毁记录
export function addRuin(data) {
  return request({
    url: '/medicalhealth/consumeruin',
    method: 'post',
    data: data
  })
}

// 修改耗材销毁记录
export function updateRuin(data) {
  return request({
    url: '/medicalhealth/consumeruin',
    method: 'put',
    data: data
  })
}

// 删除耗材销毁记录
export function delRuin(id) {
  return request({
    url: '/medicalhealth/consumeruin/' + id,
    method: 'delete'
  })
}

// 导出耗材销毁记录
export function exportRuin(query) {
  return request({
    url: '/medicalhealth/consumeruin/export',
    method: 'get',
    params: query
  })
}