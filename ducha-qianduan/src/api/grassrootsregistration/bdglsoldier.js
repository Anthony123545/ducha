import request from '@/utils/request'

// 查询军人委员会会议登记本列表
export function listBdglsoldier(query) {
  return request({
    url: '/grassrootsregistration/bdglsoldier/list',
    method: 'get',
    params: query
  })
}

// 查询军人委员会会议登记本详细
export function getBdglsoldier(id) {
  return request({
    url: '/grassrootsregistration/bdglsoldier/' + id,
    method: 'get'
  })
}

// 新增军人委员会会议登记本
export function addBdglsoldier(data) {
  return request({
    url: '/grassrootsregistration/bdglsoldier',
    method: 'post',
    data: data
  })
}

// 修改军人委员会会议登记本
export function updateBdglsoldier(data) {
  return request({
    url: '/grassrootsregistration/bdglsoldier',
    method: 'put',
    data: data
  })
}

// 删除军人委员会会议登记本
export function delBdglsoldier(id) {
  return request({
    url: '/grassrootsregistration/bdglsoldier/' + id,
    method: 'delete'
  })
}

// 导出军人委员会会议登记本
export function exportBdglsoldier(query) {
  return request({
    url: '/grassrootsregistration/bdglsoldier/export',
    method: 'get',
    params: query
  })
}

// 领导查询军人委员会会议登记本
export function getSoldierList(year) {
  return request({
    url: `/grassrootsregistration/bdglsoldier/getSoldierList`,
    method: 'get',
    params:{year:year}
  })
}


// 查询领导军人委员会会议登记本列表
export function getSoldierOnUnitList(query) {
  return request({
    url: '/grassrootsregistration/bdglsoldier/getSoldierOnUnitList',
    method: 'get',
    params: query
  })
}