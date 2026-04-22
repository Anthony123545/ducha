import request from '@/utils/request'

// 查询私家车管理列表
export function listPrivateCar(query) {
  return request({
    url: '/militaryvehicleManagement/privateCar/list',
    method: 'get',
    params: query
  })
}

// 查询现有私家车类型
export function getThePrivateBusType() {
  return request({
    url: '/militaryvehicleManagement/privateCar/getThePrivateBusType',
    method: 'get'
  })
}

// 查询私家车管理详细
export function getPrivateCar(id) {
  return request({
    url: '/militaryvehicleManagement/privateCar/' + id,
    method: 'get'
  })
}

// 新增私家车管理
export function addPrivateCar(data) {
  return request({
    url: '/militaryvehicleManagement/privateCar',
    method: 'post',
    data: data
  })
}

// 修改私家车管理
export function updatePrivateCar(data) {
  return request({
    url: '/militaryvehicleManagement/privateCar',
    method: 'put',
    data: data
  })
}

// 删除私家车管理
export function delPrivateCar(id) {
  return request({
    url: '/militaryvehicleManagement/privateCar/' + id,
    method: 'delete'
  })
}

// 导出私家车管理
export function exportPrivateCar(query) {
  return request({
    url: '/militaryvehicleManagement/privateCar/export',
    method: 'get',
    params: query
  })
}
// 查询所有人员
export function getRenYuanCar() {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
  })
}

// 查询可出私家车在位列表
export function zaiweiPrivateCar(query) {
  return request({
    url: '/militaryvehicleManagement/privateCar/zaiweiPrivateCar',
    method: 'get',
    params: query
  })
}
