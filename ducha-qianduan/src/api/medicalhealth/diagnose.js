import request from '@/utils/request'

// 查询外诊管理列表
export function listDiagnose(query) {
  return request({
    url: '/medicalhealth/diagnose/list',
    method: 'get',
    params: query
  })
}

// 查询外诊管理详细
export function getDiagnose(id) {
  return request({
    url: '/medicalhealth/diagnose/' + id,
    method: 'get'
  })
}

// 新增外诊管理
export function addDiagnose(data) {
  return request({
    url: '/medicalhealth/diagnose',
    method: 'post',
    data: data
  })
}

// 修改外诊管理
export function updateDiagnose(data) {
  return request({
    url: '/medicalhealth/diagnose',
    method: 'put',
    data: data
  })
}

// 删除外诊管理
export function delDiagnose(id) {
  return request({
    url: '/medicalhealth/diagnose/' + id,
    method: 'delete'
  })
}

// 导出外诊管理
export function exportDiagnose(query) {
  return request({
    url: '/medicalhealth/diagnose/export',
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
    params: { deptId: data }
  })
}

// 外诊管理归队
export function weiZhengGuiDui(data) {
  return request({
    url: '/medicalhealth/diagnose/update',
    method: 'put',
    data: data
  })
}