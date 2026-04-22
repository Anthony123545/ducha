import request from '@/utils/request'

// 查询疗养管理列表
export function listRecuperate(query) {
  return request({
    url: '/medicalhealth/recuperate/list',
    method: 'get',
    params: query
  })
}

// 查询疗养管理详细
export function getRecuperate(id) {
  return request({
    url: '/medicalhealth/recuperate/' + id,
    method: 'get'
  })
}

// 新增疗养管理
export function addRecuperate(data) {
  return request({
    url: '/medicalhealth/recuperate',
    method: 'post',
    data: data
  })
}

// 修改疗养管理
export function updateRecuperate(data) {
  return request({
    url: '/medicalhealth/recuperate',
    method: 'put',
    data: data
  })
}

// 删除疗养管理
export function delRecuperate(id) {
  return request({
    url: '/medicalhealth/recuperate/' + id,
    method: 'delete'
  })
}

// 导出疗养管理
export function exportRecuperate(query) {
  return request({
    url: '/medicalhealth/recuperate/export',
    method: 'get',
    params: query
  })
}

// 获取审批人用户
export function getreciuperUser(id) {
  return request({
    url: 'system/user/list',
    method: 'get',
    params: { deptId: id }
  })
}
// 获取审批人全部用户
export function getreciuperUsers() {
  return request({
    url: 'system/user/lists',
    method: 'get',
  })
}