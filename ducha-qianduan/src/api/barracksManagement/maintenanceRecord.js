import request from '@/utils/request'

// 查询五小工维修记录列表
export function listMaintenanceRecord(query) {
  return request({
    url: '/barracksManagement/maintenanceRecord/list',
    method: 'get',
    params: query
  })
}

// 查询五小工维修记录详细
export function getMaintenanceRecord(id) {
  return request({
    url: '/barracksManagement/maintenanceRecord/' + id,
    method: 'get'
  })
}

// 新增五小工维修记录
export function addMaintenanceRecord(data) {
  return request({
    url: '/barracksManagement/maintenanceRecord',
    method: 'post',
    data: data
  })
}

// 修改五小工维修记录
export function updateMaintenanceRecord(data) {
  return request({
    url: '/barracksManagement/maintenanceRecord',
    method: 'put',
    data: data
  })
}

// 删除五小工维修记录
export function delMaintenanceRecord(id) {
  return request({
    url: '/barracksManagement/maintenanceRecord/' + id,
    method: 'delete'
  })
}

// 导出五小工维修记录
export function exportMaintenanceRecord(query) {
  return request({
    url: '/barracksManagement/maintenanceRecord/export',
    method: 'get',
    params: query
  })
}