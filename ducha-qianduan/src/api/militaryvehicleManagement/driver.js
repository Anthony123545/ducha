import request from '@/utils/request'

// 查询军车司机设置列表
export function listDriver(query) {
  return request({
    url: '/militaryvehicleManagement/driver/list',
    method: 'get',
    params: query
  })
}

// 查询军车司机设置详细
export function getDriver(id) {
  return request({
    url: '/militaryvehicleManagement/driver/' + id,
    method: 'get'
  })
}

// 新增军车司机设置
export function addDriver(data) {
  return request({
    url: '/militaryvehicleManagement/driver',
    method: 'post',
    data: data
  })
}

// 修改军车司机设置
export function updateDriver(data) {
  return request({
    url: '/militaryvehicleManagement/driver',
    method: 'put',
    data: data
  })
}

// 删除军车司机设置
export function delDriver(id) {
  return request({
    url: '/militaryvehicleManagement/driver/' + id,
    method: 'delete'
  })
}

// 导出军车司机设置
export function exportDriver(query) {
  return request({
    url: '/militaryvehicleManagement/driver/export',
    method: 'get',
    params: query
  })
}