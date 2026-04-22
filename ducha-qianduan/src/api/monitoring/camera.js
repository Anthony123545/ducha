import request from '@/utils/request'

// 查询安防监控列表
export function listCamera(query) {
  return request({
    url: '/monitoring/camera/list',
    method: 'get',
    params: query
  })
}

// 查询安防监控详细
export function getCamera(id) {
  return request({
    url: '/monitoring/camera/' + id,
    method: 'get'
  })
}

// 新增安防监控
export function addCamera(data) {
  return request({
    url: '/monitoring/camera',
    method: 'post',
    data: data
  })
}

// 修改安防监控
export function updateCamera(data) {
  return request({
    url: '/monitoring/camera',
    method: 'put',
    data: data
  })
}

// 删除安防监控
export function delCamera(id) {
  return request({
    url: '/monitoring/camera/' + id,
    method: 'delete'
  })
}

// 导出安防监控
export function exportCamera(query) {
  return request({
    url: '/monitoring/camera/export',
    method: 'get',
    params: query
  })
}
