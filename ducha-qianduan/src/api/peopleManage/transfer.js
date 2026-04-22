import request from '@/utils/request'

// 查询调动管理列表
export function listTransfer(query) {
  return request({
    url: '/peopleManage/transfer/list',
    method: 'get',
    params: query
  })
}

// 查询调动管理详细
export function getTransfer(id) {
  return request({
    url: '/peopleManage/transfer/' + id,
    method: 'get'
  })
}

// 新增调动管理
export function addTransfer(data) {
  return request({
    url: '/peopleManage/transfer',
    method: 'post',
    data: data
  })
}

// 修改调动管理
export function updateTransfer(data) {
  return request({
    url: '/peopleManage/transfer',
    method: 'put',
    data: data
  })
}

// 删除调动管理
export function delTransfer(id) {
  return request({
    url: '/peopleManage/transfer/' + id,
    method: 'delete'
  })
}

// 导出调动管理
export function exportTransfer(query) {
  return request({
    url: '/peopleManage/transfer/export',
    method: 'get',
    params: query
  })
}

// 查询政审对象管理列表
export function listPeople(query) {
  return request({
    url: '/peopleManage/people/list',
    method: 'get',
    params: query
  })
}

// 查询政审对象基本信息
export function getPeople(id) {
  return request({
    url: '/peopleManage/people/' + id,
    method: 'get'
  })
}

// 查询部门 
export function getDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}