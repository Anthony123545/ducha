import request from '@/utils/request'

// 查询网络/手机/涉密载体登记本列表
export function listBdglsecrecy(query) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy/list',
    method: 'get',
    params: query
  })
}

// 查询网络/手机/涉密载体登记本详细
export function getBdglsecrecy(id) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy/' + id,
    method: 'get'
  })
}
// 查看/手机/涉密载体登记本详细
export function units(query) {
  return request({
    url: `/grassrootsregistration/bdglsecrecy/units`,
    method: 'get',
    params: query
  })
}

// 新增网络/手机/涉密载体登记本
export function addBdglsecrecy(data) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy',
    method: 'post',
    data: data
  })
}

// 修改网络/手机/涉密载体登记本
export function updateBdglsecrecy(data) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy',
    method: 'put',
    data: data
  })
}

// 删除网络/手机/涉密载体登记本
export function delBdglsecrecy(id) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy/' + id,
    method: 'delete'
  })
}

// 导出网络/手机/涉密载体登记本
export function exportBdglsecrecy(query) {
  return request({
    url: '/grassrootsregistration/bdglsecrecy/export',
    method: 'get',
    params: query
  })
}

// 领导列表
export function getSecrecyStats(year) {
  return request({
    url: `/grassrootsregistration/bdglsecrecy/getSecrecyStats`,
    method: 'get',
    params:{year:year}
  })
}