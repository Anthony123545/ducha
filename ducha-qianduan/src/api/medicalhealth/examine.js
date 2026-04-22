import request from '@/utils/request'

// 查询人员体检信息列表
export function listExamine(query) {
  return request({
    url: '/medicalhealth/examine/list',
    method: 'get',
    params: query
  })
}

// 查询人员体检信息详细
export function getExamine(id) {
  return request({
    url: '/medicalhealth/examine/' + id,
    method: 'get'
  })
}

// 新增人员体检信息
export function addExamine(data) {
  return request({
    url: '/medicalhealth/examine',
    method: 'post',
    data: data
  })
}

// 修改人员体检信息
export function updateExamine(data) {
  return request({
    url: '/medicalhealth/examine',
    method: 'put',
    data: data
  })
}

// 新增人员体检信息表
export function details(data) {
  return request({
    url: '/medicalhealth/details',
    method: 'post',
    data: data
  })
}


// 删除人员体检信息
export function delExamine(id) {
  return request({
    url: '/medicalhealth/examine/' + id,
    method: 'delete'
  })
}

// 删除
export function delshanchu(id) {
  return request({
    url: '/medicalhealth/details/' + id,
    method: 'delete'
  })
}

// 导出人员体检信息
export function exportExamine(query) {
  return request({
    url: '/medicalhealth/examine/export',
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
// 查找主持人/人员
export function getZhuChiRen(data) {
  return request({
    url: '/grassrootsregistration/common/getZhuChiRen',
    method: 'get',
    params: {
      deptId: data
    }
  })
}

// 统计
export function getPeopleExaminationcount(query) {
  return request({
    url: '/peopleManage/people/getPeopleExaminationcount',
    method: 'get',
    params: query
  })
}

// 查询人员体检信息列表
export function getlist(query) {
  return request({
    url: 'peopleManage/people/getPeopleExaminationList',
    method: 'get',
    params: query
  })
}

//体检完成
export function Examinationedit(data) {
  return request({
    url: '/peopleManage/people/Examinationedit',
    method: 'put',
    data: data
  })
}
