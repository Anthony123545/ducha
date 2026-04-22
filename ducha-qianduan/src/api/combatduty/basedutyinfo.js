import request from '@/utils/request'

// 查询基地值班列表
export function listBasedutyinfo(query) {
  return request({
    url: '/combatduty/basedutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询基地值班详细
export function getBasedutyinfo(id) {
  return request({
    url: '/combatduty/basedutyinfo/' + id,
    method: 'get'
  })
}

// 新增基地值班
export function addBasedutyinfo(data) {
  return request({
    url: '/combatduty/basedutyinfo',
    method: 'post',
    data: data
  })
}

// 修改基地值班
export function updateBasedutyinfo(data) {
  return request({
    url: '/combatduty/basedutyinfo',
    method: 'put',
    data: data
  })
}

// 删除基地值班
export function delBasedutyinfo(id) {
  return request({
    url: '/combatduty/basedutyinfo/' + id,
    method: 'delete'
  })
}

// 导出基地值班
export function exportBasedutyinfo(query) {
  return request({
    url: '/combatduty/basedutyinfo/export',
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