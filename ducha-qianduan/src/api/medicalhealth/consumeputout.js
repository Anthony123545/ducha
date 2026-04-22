import request from '@/utils/request'

// 查询耗材出库登记列表
export function listConsumeputout(query) {
  return request({
    url: '/medicalhealth/consumeputout/list',
    method: 'get',
    params: query
  })
}

// 查询耗材出库登记详细
export function getConsumeputout(id) {
  return request({
    url: '/medicalhealth/consumeputout/' + id,
    method: 'get'
  })
}

// 新增耗材出库登记
export function addConsumeputout(data) {
  return request({
    url: '/medicalhealth/consumeputout',
    method: 'post',
    data: data
  })
}

// 修改耗材出库登记
export function updateConsumeputout(data) {
  return request({
    url: '/medicalhealth/consumeputout',
    method: 'put',
    data: data
  })
}

// 删除耗材出库登记
export function delConsumeputout(id) {
  return request({
    url: '/medicalhealth/consumeputout/' + id,
    method: 'delete'
  })
}

// 导出耗材出库登记
export function exportConsumeputout(query) {
  return request({
    url: '/medicalhealth/consumeputout/export',
    method: 'get',
    params: query
  })
}