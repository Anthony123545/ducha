import request from '@/utils/request'

// 查询私家车进出查询列表
export function listPrivateAccessLog(query) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog/list',
    method: 'get',
    params: query
  })
}

// 查询私家车进出查询详细
export function getPrivateAccessLog(id) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog/' + id,
    method: 'get'
  })
}

// 新增私家车进出查询
export function addPrivateAccessLog(data) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog',
    method: 'post',
    data: data
  })
}

// 修改私家车进出查询
export function updatePrivateAccessLog(data) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog',
    method: 'put',
    data: data
  })
}

// 删除私家车进出查询
export function delPrivateAccessLog(id) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog/' + id,
    method: 'delete'
  })
}

// 导出私家车进出查询
export function exportPrivateAccessLog(query) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog/export',
    method: 'get',
    params: query
  })
}
