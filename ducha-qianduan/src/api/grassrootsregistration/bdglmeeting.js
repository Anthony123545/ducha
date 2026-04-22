import request from '@/utils/request'

// 查询团支部会议登记本列表
export function listBdglmeeting(query) {
  return request({
    url: '/grassrootsregistration/bdglmeeting/list',
    method: 'get',
    params: query
  })
}

// 查询团支部会议登记本详细
export function getBdglmeeting(id) {
  return request({
    url: '/grassrootsregistration/bdglmeeting/' + id,
    method: 'get'
  })
}

// 新增团支部会议登记本
export function addBdglmeeting(data) {
  return request({
    url: '/grassrootsregistration/bdglmeeting',
    method: 'post',
    data: data
  })
}

// 修改团支部会议登记本
export function updateBdglmeeting(data) {
  return request({
    url: '/grassrootsregistration/bdglmeeting',
    method: 'put',
    data: data
  })
}

// 删除团支部会议登记本
export function delBdglmeeting(id) {
  return request({
    url: '/grassrootsregistration/bdglmeeting/' + id,
    method: 'delete'
  })
}

// 导出团支部会议登记本
export function exportBdglmeeting(query) {
  return request({
    url: '/grassrootsregistration/bdglmeeting/export',
    method: 'get',
    params: query
  })
}
// 查询部门 
export function getDept(query) {
  return request({
    url: '/grassrootsregistration/common/getDept',
    method: 'get',
    params: query
  })
}
// 查询部门2 
export function getDept2() {
  return request({
    url: '/grassrootsregistration/common/getDept',
    method: 'get',
    params: { type: '1' }
  })
}

// 查找主持人/人员
export function getZhuChiRen(data) {
  return request({
    url: '/grassrootsregistration/common/getZhuChiRen',
    method: 'get',
    params: { deptId: data }
  })
}
// 查询所在单位首长(用户，不包含自己)
export function getUsers(data) {
  return request({
    url: '/grassrootsregistration/common/getUsers',
    method: 'get',
    params: { deptId: data }
  })
}
// 查询所有人员首长(用户)
export function getUser(data) {
  return request({
    url: '/grassrootsregistration/common/getUser',
    method: 'get',
    params: { deptId: data }
  })
}
// 查询所在单位首长(用户，包含自己)
export function getUserzs(data) {
  return request({
    url: 'system/user/list',
    method: 'get',
    params: { deptId: data }
  })
}
// 获取实力情况
export function getShiLi(query) {
  return request({
    url: '/grassrootsregistration/bdgldiary/getRenShu',
    method: 'get',
    params: { deptId: query }
  })
}
// 获取小绿小红状态
export function getRed(query) {
  return request({
    url: '/grassrootsregistration/bdgldiary/grtWeiSP',
    method: 'get',
  })
}

// 获取训练计划
export function getXunLian(unitId, blogDate) {
  return request({
    url: '/grassrootsregistration/bdgldiary/getWeekWork',
    method: 'get',
    params: {
      unitId,
      blogDate
    }
  })
}

// 获取外面单位树形
export function getWeiShu(query) {
  return request({
    url: '/grassrootsregistration/common/get',
    method: 'get',
  })
}

// 获取在线人员
export function getZaiRen(data, reign) {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
    params: { deptId: data, reign }
  })
}
// 获取营级人员所有
export function getQuanBu(data, reign) {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
    params: { deptId: data, reign }
  })
}

// 获取人员名册
export function getRenYuan() {
  return request({
    url: '/grassrootsregistration/personnelRoster/getDeptXiangQi',
    method: 'get',
    params: { deptId: data, reign }
  })
}

// 获取部门列表
export function getBuMen() {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: { deptId: data, reign }
  })
}

// 获取外面部门数据
export function getShouBu(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
  })
}

// 获取所有人员列表
export function getAll(query) {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
  })
}

// 获取人员名册列表
export function getXiangQis(query) {
  return request({
    url: '/grassrootsregistration/personnelRoster/getPeopleList',
    method: 'get',
    params: query
  })
}

// 查看人员名册列表
export function getRenYuanXiangQing(query) {
  return request({
    url: '/grassrootsregistration/personnelRoster/getDeptXiangQi',
    method: 'get',
    params: { deptId: query }
  })
}

// 领导本列表
export function getMeetingList(year) {
  return request({
    url: `/grassrootsregistration/bdglmeeting/getMeetingList`,
    method: 'get',
    params:{year:year}
  })
}


// 查询领导团支部会议登记本列表
export function getMeetingOnUnitList(query) {
  return request({
    url: '/grassrootsregistration/bdglmeeting/getMeetingOnUnitList',
    method: 'get',
    params: query
  })
}