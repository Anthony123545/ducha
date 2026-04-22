import request from '@/utils/request'

// 查询基层值班列表
export function listGrassdutyinfo(query) {
  return request({
    url: '/combatduty/grassdutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询基层值班详细
export function getGrassdutyinfo(id) {
  return request({
    url: '/combatduty/grassdutyinfo/' + id,
    method: 'get'
  })
}

// 新增基层值班
export function addGrassdutyinfo(data) {
  return request({
    url: '/combatduty/grassdutyinfo',
    method: 'post',
    data: data
  })
}

// 修改基层值班
export function updateGrassdutyinfo(data) {
  return request({
    url: '/combatduty/grassdutyinfo',
    method: 'put',
    data: data
  })
}

// 删除基层值班
export function delGrassdutyinfo(id) {
  return request({
    url: '/combatduty/grassdutyinfo/' + id,
    method: 'delete'
  })
}

// 导出基层值班
export function exportGrassdutyinfo(query) {
  return request({
    url: '/combatduty/grassdutyinfo/export',
    method: 'get',
    params: query
  })
}

// 查询人员管理列表
export function listPeople(query) {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
    params: query
  })
}
// 翻译查询人员管理列表
export function listPeoples(query) {
  return request({
    url: '/peopleManage/people/getlist',
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

// 值班要素列表
export function todaydutyinfo(query) {
  return request({
    url: '/combatduty/grassdutyinfo/todaydutyinfo',
    method: 'get',
    params: query
  })
}
