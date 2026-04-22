import request from '@/utils/request'

// 查询军械装备登记簿列表
export function listBdglarmament(query) {
  return request({
    url: '/grassrootsregistration/bdglarmament/list',
    method: 'get',
    params: query
  })
}

// 查询军械装备登记簿详细
export function getBdglarmament(id) {
  return request({
    url: '/grassrootsregistration/bdglarmament/' + id,
    method: 'get'
  })
}
// 查看军械装备登记簿详细
export function armamentunits(query) {
  return request({
    url: '/grassrootsregistration/bdglarmament/armamentunits',
    method: 'get',
    params: query
  })
}


// 新增军械装备登记簿
export function addBdglarmament(data) {
  return request({
    url: '/grassrootsregistration/bdglarmament',
    method: 'post',
    data: data
  })
}

// 修改军械装备登记簿
export function updateBdglarmament(data) {
  return request({
    url: '/grassrootsregistration/bdglarmament',
    method: 'put',
    data: data
  })
}

// 删除军械装备登记簿
export function delBdglarmament(id) {
  return request({
    url: '/grassrootsregistration/bdglarmament/' + id,
    method: 'delete'
  })
}

// 导出军械装备登记簿
export function exportBdglarmament(query) {
  return request({
    url: '/grassrootsregistration/bdglarmament/export',
    method: 'get',
    params: query
  })
}

// 领导列表
export function armamentList(year) {
  return request({
    url: `/grassrootsregistration/bdglarmament/armamentList`,
    method: 'get',
    params:{year:year}
  })
}
