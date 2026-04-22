import request from '@/utils/request'

// 查询执行任务列表
export function listTaskdutyinfo(query) {
  return request({
    url: '/combatduty/taskdutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询执行任务详细
export function getTaskdutyinfo(id) {
  return request({
    url: '/combatduty/taskdutyinfo/' + id,
    method: 'get'
  })
}

// 新增执行任务
export function addTaskdutyinfo(data) {
  return request({
    url: '/combatduty/taskdutyinfo',
    method: 'post',
    data: data
  })
}

// 修改执行任务
export function updateTaskdutyinfo(data) {
  return request({
    url: '/combatduty/taskdutyinfo',
    method: 'put',
    data: data
  })
}

// 删除执行任务
export function delTaskdutyinfo(id) {
  return request({
    url: '/combatduty/taskdutyinfo/' + id,
    method: 'delete'
  })
}

// 导出执行任务
export function exportTaskdutyinfo(query) {
  return request({
    url: '/combatduty/taskdutyinfo/export',
    method: 'get',
    params: query
  })
}

// 查询人员管理列表
export function listPeople(query) {
  return request({
    url: '/peopleManage/people/list',
    method: 'get',
    params: query
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/system/dept/treeselect',
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
