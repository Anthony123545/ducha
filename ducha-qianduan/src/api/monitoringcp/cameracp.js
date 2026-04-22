import request from '@/utils/request'

// 查询安防监控列表
export function listCameracp(query) {
  return request({
    url: '/monitoringcp/cameracp/list',
    method: 'get',
    params: query
  })
}

// 查询安防监控详细
export function getCameracp(id) {
  return request({
    url: '/monitoringcp/cameracp/' + id,
    method: 'get'
  })
}

// 新增安防监控
export function addCameracp(data) {
  return request({
    url: '/monitoringcp/cameracp',
    method: 'post',
    data: data
  })
}

// 修改安防监控
export function updateCameracp(data) {
  return request({
    url: '/monitoringcp/cameracp',
    method: 'put',
    data: data
  })
}

// 删除安防监控
export function delCameracp(id) {
  return request({
    url: '/monitoringcp/cameracp/' + id,
    method: 'delete'
  })
}

// 导出安防监控
export function exportCameracp(query) {
  return request({
    url: '/monitoringcp/cameracp/export',
    method: 'get',
    params: query
  })
}