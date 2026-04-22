import request from '@/utils/request'

// 查询军车管理列表
export function listThebus(query) {
  return request({
    url: '/militaryvehicleManagement/thebus/list',
    method: 'get',
    params: query
  })
}

// 查询军车管理详细
export function getThebus(id) {
  return request({
    url: '/militaryvehicleManagement/thebus/' + id,
    method: 'get'
  })
}

// 新增军车管理
export function addThebus(data) {
  return request({
    url: '/militaryvehicleManagement/thebus',
    method: 'post',
    data: data
  })
}

// 修改军车管理
export function updateThebus(data) {
  return request({
    url: '/militaryvehicleManagement/thebus',
    method: 'put',
    data: data
  })
}

// 删除军车管理
export function delThebus(id) {
  return request({
    url: '/militaryvehicleManagement/thebus/' + id,
    method: 'delete'
  })
}

// 导出军车管理
export function exportThebus(query) {
  return request({
    url: '/militaryvehicleManagement/thebus/export',
    method: 'get',
    params: query
  })
}

// 查询部门
export function getDept(query) {
  return request({
    url: '/grassrootsregistration/common/getDept',
    method: 'get',
    params: query
  })
}

// 查找主持人
export function getZhuChiRen(data) {
  return request({
    url: '/grassrootsregistration/common/getZhuChiRen',
    method: 'get',
    params: { deptId: data }
  })
}
// 查询军车司机设置列表
export function getRen(data) {
  return request({
    url: '/militaryvehicleManagement/driver/list',
    method: 'get',
    params: { unitId: data }
  })
}

// 查询现有军车类型
export function getThebusType() {
  return request({
    url: '/militaryvehicleManagement/thebus/getThebusType',
    method: 'get'
  })
}
