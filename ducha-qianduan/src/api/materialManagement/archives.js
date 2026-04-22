import request from '@/utils/request'

// 查询物资档案列表
export function listArchives(query) {
  return request({
    url: '/materialManagement/archives/list',
    method: 'get',
    params: query
  })
}

// 查询物资档案详细
export function getArchives(id) {
  return request({
    url: '/materialManagement/archives/' + id,
    method: 'get'
  })
}

// 新增物资档案
export function addArchives(data) {
  return request({
    url: '/materialManagement/archives',
    method: 'post',
    data: data
  })
}

// 修改物资档案
export function updateArchives(data) {
  return request({
    url: '/materialManagement/archives',
    method: 'put',
    data: data
  })
}

// 删除物资档案
export function delArchives(id) {
  return request({
    url: '/materialManagement/archives/' + id,
    method: 'delete'
  })
}

// 导出物资档案
export function exportArchives(query) {
  return request({
    url: '/materialManagement/archives/export',
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