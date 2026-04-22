import request from '@/utils/request'

// 查询临时来队列表
export function listCheckin(query) {
  return request({
    url: '/barracksManagement/checkin/list',
    method: 'get',
    params: query
  })
}

// 查询临时来队详细
export function getCheckin(id) {
  return request({
    url: '/barracksManagement/checkin/' + id,
    method: 'get'
  })
}

// 新增临时来队
export function addCheckin(data) {
  return request({
    url: '/barracksManagement/checkin',
    method: 'post',
    data: data
  })
}

// 修改临时来队
export function updateCheckin(data) {
  return request({
    url: '/barracksManagement/checkin',
    method: 'put',
    data: data
  })
}

// 删除临时来队
export function delCheckin(id) {
  return request({
    url: '/barracksManagement/checkin/' + id,
    method: 'delete'
  })
}

// 导出临时来队
export function exportCheckin(query) {
  return request({
    url: '/barracksManagement/checkin/export',
    method: 'get',
    params: query
  })
}