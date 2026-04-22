import request from '@/utils/request'

// 查询涉密计算机台账列表
export function listSecretaccount(query) {
  return request({
    url: '/bdglSecret/secretaccount/list',
    method: 'get',
    params: query
  })
}

// 查询涉密计算机台账详细
export function getSecretaccount(id) {
  return request({
    url: '/bdglSecret/secretaccount/' + id,
    method: 'get'
  })
}

// 新增涉密计算机台账
export function addSecretaccount(data) {
  return request({
    url: '/bdglSecret/secretaccount',
    method: 'post',
    data: data
  })
}

// 修改涉密计算机台账
export function updateSecretaccount(data) {
  return request({
    url: '/bdglSecret/secretaccount',
    method: 'put',
    data: data
  })
}

// 删除涉密计算机台账
export function delSecretaccount(id) {
  return request({
    url: '/bdglSecret/secretaccount/' + id,
    method: 'delete'
  })
}

// 查询统计
export function getNumber() {
  return request({
    url: '/bdglSecret/secretaccount/getNumber',
    method: 'get'
  })
}

// 审批获取所有单位
export function getAllDept() {
  return request({
    url: 'grassrootsregistration/common/getAllDept',
    method: 'get'
  })
}