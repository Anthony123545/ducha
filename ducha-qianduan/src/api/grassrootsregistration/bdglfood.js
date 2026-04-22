import request from '@/utils/request'

// 查询伙食管理登记薄列表
export function listBdglfood(query) {
  return request({
    url: '/grassrootsregistration/bdglfood/list',
    method: 'get',
    params: query
  })
}

// 查询伙食管理登记薄详细
export function getBdglfood(id) {
  return request({
    url: '/grassrootsregistration/bdglfood/' + id,
    method: 'get'
  })
}

// 新增伙食管理登记薄
export function addBdglfood(data) {
  return request({
    url: '/grassrootsregistration/bdglfood',
    method: 'post',
    data: data
  })
}

// 修改伙食管理登记薄
export function updateBdglfood(data) {
  return request({
    url: '/grassrootsregistration/bdglfood',
    method: 'put',
    data: data
  })
}

// 删除伙食管理登记薄
export function delBdglfood(id) {
  return request({
    url: '/grassrootsregistration/bdglfood/' + id,
    method: 'delete'
  })
}

// 导出伙食管理登记薄
export function exportBdglfood(query) {
  return request({
    url: '/grassrootsregistration/bdglfood/export',
    method: 'get',
    params: query
  })
}