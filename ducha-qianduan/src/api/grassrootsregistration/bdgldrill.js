import request from '@/utils/request'

// 查询军事训练登记簿列表
export function listBdgldrill(query) {
  return request({
    url: '/grassrootsregistration/bdgldrill/list',
    method: 'get',
    params: query
  })
}

// 查看军事训练登记簿详细
export function getInfoByUnitId(query) {
  return request({
    url: '/grassrootsregistration/bdgldrill/getInfoByUnitId',
    method: 'get',
    params: query
  })
}
// 修改军事训练登记簿详细
export function getBdgldrill(id) {
  return request({
    url: '/grassrootsregistration/bdgldrill/' + id,
    method: 'get'
  })
}

// 新增军事训练登记簿
export function addBdgldrill(data) {
  return request({
    url: '/grassrootsregistration/bdgldrill',
    method: 'post',
    data: data
  })
}

// 修改军事训练登记簿
export function updateBdgldrill(data) {
  return request({
    url: '/grassrootsregistration/bdgldrill',
    method: 'put',
    data: data
  })
}

// 删除军事训练登记簿
export function delBdgldrill(id) {
  return request({
    url: '/grassrootsregistration/bdgldrill/' + id,
    method: 'delete'
  })
}

// 导出军事训练登记簿
export function exportBdgldrill(query) {
  return request({
    url: '/grassrootsregistration/bdgldrill/export',
    method: 'get',
    params: query
  })
}

export function treeselect(query) {
  return request({
    url: `/grassrootsregistration/type/treeselect?parentId=${0}`,
    method: 'get',
    params: query
  })
}

export function getweekwork(query) {
  return request({
    url: `/grassrootsregistration/bdgldrill/getweekwork`,
    method: 'get',
    params: query
  })
}
// 删除修改中的列表数据
export function drillFu(id) {
  return request({
    url: '/grassrootsregistration/drillFu/' + id,
    method: 'delete'
  })
}


export function treeselects(id) {
  return request({
    url: `/grassrootsregistration/type/` + id,
    method: 'get',
  })
}

// 领导列表查看
export function drillList(year) {
  return request({
    url: `/grassrootsregistration/bdgldrill/drillList`,
    method: 'get',
    params:{year:year}
  })
}
