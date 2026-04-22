import request from '@/utils/request'

// 查询食品分类列表
export function listCookcategory(query) {
  return request({
    url: '/system/cookcategory/list',
    method: 'get',
    params: query
  })
}

// 查询食品分类详细
export function getCookcategory(id) {
  return request({
    url: '/system/cookcategory/' + id,
    method: 'get'
  })
}

// 新增食品分类
export function addCookcategory(data) {
  return request({
    url: '/system/cookcategory',
    method: 'post',
    data: data
  })
}

// 修改食品分类
export function updateCookcategory(data) {
  return request({
    url: '/system/cookcategory',
    method: 'put',
    data: data
  })
}

// 删除食品分类
export function delCookcategory(id) {
  return request({
    url: '/system/cookcategory/' + id,
    method: 'delete'
  })
}

// 导出食品分类
export function exportCookcategory(query) {
  return request({
    url: '/system/cookcategory/export',
    method: 'get',
    params: query
  })
}