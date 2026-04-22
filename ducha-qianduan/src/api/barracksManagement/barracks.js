import request from '@/utils/request'

// 查询营房档案列表
export function listBarracks(query) {
  return request({
    url: '/barracksManagement/barracks/list',
    method: 'get',
    params: query
  })
}

// 查询营房档案详细
export function getBarracks(id) {
  return request({
    url: '/barracksManagement/barracks/' + id,
    method: 'get'
  })
}

// 新增营房档案
export function addBarracks(data) {
  return request({
    url: '/barracksManagement/barracks',
    method: 'post',
    data: data
  })
}

// 修改营房档案
export function updateBarracks(data) {
  return request({
    url: '/barracksManagement/barracks',
    method: 'put',
    data: data
  })
}

// 删除营房档案
export function delBarracks(id) {
  return request({
    url: '/barracksManagement/barracks/' + id,
    method: 'delete'
  })
}

// 导出营房档案
export function exportBarracks(query) {
  return request({
    url: '/barracksManagement/barracks/export',
    method: 'get',
    params: query
  })
}

// 营房统计
export function getnumber(query) {
  return request({
    url: '/barracksManagement/barracks/getnumber',
    method: 'get',
    params: query
  })
}
