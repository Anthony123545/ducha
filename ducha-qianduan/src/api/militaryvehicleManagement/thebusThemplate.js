import request from '@/utils/request'

// 查询军车审批模板列表
export function listThebusThemplate(query) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate/list',
    method: 'get',
    params: query
  })
}

// 查询军车审批模板详细
export function getThebusThemplate(id) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate/' + id,
    method: 'get'
  })
}

// 新增军车审批模板
export function addThebusThemplate(data) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate',
    method: 'post',
    data: data
  })
}

// 修改军车审批模板
export function updateThebusThemplate(data) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate',
    method: 'put',
    data: data
  })
}

// 删除军车审批模板
export function delThebusThemplate(id) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate/' + id,
    method: 'delete'
  })
}

// 导出军车审批模板
export function exportThebusThemplate(query) {
  return request({
    url: '/militaryvehicleManagement/thebusThemplate/export',
    method: 'get',
    params: query
  })
}
