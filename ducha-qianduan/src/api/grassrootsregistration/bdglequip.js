import request from '@/utils/request'

// 查询训练器材/教材登记薄列表
export function listBdglequip(query) {
  return request({
    url: '/grassrootsregistration/bdglequip/list',
    method: 'get',
    params: query
  })
}

// 查询训练器材/教材登记薄详细
export function getBdglequip(id) {
  return request({
    url: '/grassrootsregistration/bdglequip/' + id,
    method: 'get'
  })
}

// 查看训练器材/教材登记薄详细
export function equipuntits(query) {
  return request({
    url: '/grassrootsregistration/bdglequip/equipunits' ,
    method: 'get',
    params: query
  })
}
// 预览训练器材/教材登记薄
export function lookequipuntits(id) {
  return request({
    url: '/grassrootsregistration/bdglequip/exportEquip?unitid=' + id,
    method: 'get'
  })
}

// 新增训练器材/教材登记薄
export function addBdglequip(data) {
  return request({
    url: '/grassrootsregistration/bdglequip',
    method: 'post',
    data: data
  })
}

// 修改训练器材/教材登记薄
export function updateBdglequip(data) {
  return request({
    url: '/grassrootsregistration/bdglequip',
    method: 'put',
    data: data
  })
}

// 删除训练器材/教材登记薄
export function delBdglequip(id) {
  return request({
    url: '/grassrootsregistration/bdglequip/' + id,
    method: 'delete'
  })
}

// 导出训练器材/教材登记薄
export function exportBdglequip(query) {
  return request({
    url: '/grassrootsregistration/bdglequip/export',
    method: 'get',
    params: query
  })
}

// 获取部门列表
export function getList(query) {
  return request({
    url: '/system/dept/list ',
    method: 'get',
    params: query
  })
}

// 查看领导列表
export function equipList(year) {
  return request({
    url: `/grassrootsregistration/bdglequip/equipList`,
    method: 'get',
    params:{year:year}
  })
}