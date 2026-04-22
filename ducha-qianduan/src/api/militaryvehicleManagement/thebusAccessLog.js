import request from '@/utils/request'

// 查询军车进出查询列表
export function listThebusAccessLog(query) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog/list',
    method: 'get',
    params: query
  })
}

// 查询军车进出查询详细
export function getThebusAccessLog(id) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog/' + id,
    method: 'get'
  })
}

// 新增军车进出查询
export function addThebusAccessLog(data) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog',
    method: 'post',
    data: data
  })
}

// 修改军车进出查询
export function updateThebusAccessLog(data) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog',
    method: 'put',
    data: data
  })
}

// 删除军车进出查询
export function delThebusAccessLog(id) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog/' + id,
    method: 'delete'
  })
}

// 导出军车进出查询
export function exportThebusAccessLog(query) {
  return request({
    url: '/militaryvehicleManagement/thebusAccessLog/export',
    method: 'get',
    params: query
  })
}