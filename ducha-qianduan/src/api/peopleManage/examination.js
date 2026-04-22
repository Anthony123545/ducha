import request from '@/utils/request'

// 查询人员政审列表
export function listExamination(query) {
  return request({
    url: '/peopleManage/examination/list',
    method: 'get',
    params: query
  })
}

// 查询人员政审详细
export function getExamination(id) {
  return request({
    url: '/peopleManage/examination/' + id,
    method: 'get'
  })
}

// 新增人员政审
export function addExamination(data) {
  return request({
    url: '/peopleManage/examination',
    method: 'post',
    data: data
  })
}

// 修改人员政审
export function updateExamination(data) {
  return request({
    url: '/peopleManage/examination',
    method: 'put',
    data: data
  })
}

// 删除人员政审
export function delExamination(id) {
  return request({
    url: '/peopleManage/examination/' + id,
    method: 'delete'
  })
}

// 导出人员政审
export function exportExamination(query) {
  return request({
    url: '/peopleManage/examination/export',
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