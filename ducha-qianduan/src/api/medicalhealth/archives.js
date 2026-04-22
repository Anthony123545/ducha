import request from '@/utils/request'

// 查询耗材库存档案列表
export function listArchives(query) {
  return request({
    url: '/medicalhealth/archives/list',
    method: 'get',
    params: query
  })
}

// 查询耗材库存档案详细
export function getArchives(id) {
  return request({
    url: '/medicalhealth/archives/' + id,
    method: 'get'
  })
}

// 新增耗材库存档案
export function addArchives(data) {
  return request({
    url: '/medicalhealth/archives',
    method: 'post',
    data: data
  })
}

// 修改耗材库存档案
export function updateArchives(data) {
  return request({
    url: '/medicalhealth/archives',
    method: 'put',
    data: data
  })
}

// 删除耗材库存档案
export function delArchives(id) {
  return request({
    url: '/medicalhealth/archives/' + id,
    method: 'delete'
  })
}

// 导出耗材库存档案
export function exportArchives(query) {
  return request({
    url: '/medicalhealth/archives/export',
    method: 'get',
    params: query
  })
}


