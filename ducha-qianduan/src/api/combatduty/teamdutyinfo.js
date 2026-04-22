import request from '@/utils/request'

// 查询大队值班列表
export function listTeamdutyinfo(query) {
  return request({
    url: '/combatduty/teamdutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询大队值班详细
export function getTeamdutyinfo(id) {
  return request({
    url: '/combatduty/teamdutyinfo/' + id,
    method: 'get'
  })
}

// 新增大队值班
export function addTeamdutyinfo(data) {
  return request({
    url: '/combatduty/teamdutyinfo',
    method: 'post',
    data: data
  })
}

// 修改大队值班
export function updateTeamdutyinfo(data) {
  return request({
    url: '/combatduty/teamdutyinfo',
    method: 'put',
    data: data
  })
}

// 删除大队值班
export function delTeamdutyinfo(id) {
  return request({
    url: '/combatduty/teamdutyinfo/' + id,
    method: 'delete'
  })
}

// 导出大队值班
export function exportTeamdutyinfo(query) {
  return request({
    url: '/combatduty/teamdutyinfo/export',
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

// 删除新增按钮当前数据
export function delTeamdutyinfos(id) {
  return request({
    url: '/combatduty/teamdutyFu/' + id,
    method: 'delete'
  })
}