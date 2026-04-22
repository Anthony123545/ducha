import request from '@/utils/request'

// 查询营产，公物管理登记簿列表
export function listBdglpublicproperty(query) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty/list',
    method: 'get',
    params: query
  })
}

// 查询营产，公物管理登记簿详细
export function getBdglpublicproperty(id) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty/' + id,
    method: 'get'
  })
}

// 查看营产，公物管理登记簿详细
export function publiccpropertyunits(query) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty/publiccpropertyunits',
    method: 'get',
    params: query
  })
}

// 新增营产，公物管理登记簿
export function addBdglpublicproperty(data) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty',
    method: 'post',
    data: data
  })
}

// 修改营产，公物管理登记簿
export function updateBdglpublicproperty(data) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty',
    method: 'put',
    data: data
  })
}

// 删除营产，公物管理登记簿
export function delBdglpublicproperty(id) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty/' + id,
    method: 'delete'
  })
}

// 导出营产，公物管理登记簿
export function exportBdglpublicproperty(query) {
  return request({
    url: '/grassrootsregistration/bdglpublicproperty/export',
    method: 'get',
    params: query
  })
}

// 领导列表
export function propertyList(year) {
  return request({
    url: `/grassrootsregistration/bdglpublicproperty/propertyList`,
    method: 'get',
    params:{year:year}
  })
}
