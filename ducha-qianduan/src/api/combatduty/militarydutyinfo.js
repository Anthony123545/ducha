import request from '@/utils/request'

// 查询军车值班列表
export function listMilitarydutyinfo(query) {
  return request({
    url: '/combatduty/militarydutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询军车值班详细
export function getMilitarydutyinfo(id) {
  return request({
    url: '/combatduty/militarydutyinfo/' + id,
    method: 'get'
  })
}

// 新增军车值班
export function addMilitarydutyinfo(data) {
  return request({
    url: '/combatduty/militarydutyinfo',
    method: 'post',
    data: data
  })
}

// 修改军车值班
export function updateMilitarydutyinfo(data) {
  return request({
    url: '/combatduty/militarydutyinfo',
    method: 'put',
    data: data
  })
}

// 删除军车值班
export function delMilitarydutyinfo(id) {
  return request({
    url: '/combatduty/militarydutyinfo/' + id,
    method: 'delete'
  })
}

// 导出军车值班
export function exportMilitarydutyinfo(query) {
  return request({
    url: '/combatduty/militarydutyinfo/export',
    method: 'get',
    params: query
  })
}

// 获取人力资源司机名字
export function listPeople(query) {
  return request({
    url: '/peopleManage/people/list2',
    method: 'get',
    params: query
  })
}
// 查询人员管理列表
export function listPeoples(query) {
  return request({
    url: '/grassrootsregistration/common/list',
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


//车牌号
export function getthebus(status) {
  return request({
    url: '/militaryvehicleManagement/thebus/list',
    method: 'get',
    params: { status: status }
  })
}


// 更改军车状态
export function updatestatus() {
  return request({
    url: '/militaryvehicleManagement/thebus/updatestatus',
    method: 'put',
  })
}


// export function listPeople(query) {
//   return request({
//     url: '/deviceManage/people/lis2t',
//     method: 'get',
//     params: query
//   })
// }
