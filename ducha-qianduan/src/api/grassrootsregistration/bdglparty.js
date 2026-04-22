import request from '@/utils/request'

// 查询党支部会议登记本列表
export function listBdglparty(query) {
  return request({
    url: '/grassrootsregistration/bdglparty/list',
    method: 'get',
    params: query
  })
}

// 查询党支部会议登记本详细
export function getBdglparty(id) {
  return request({
    url: '/grassrootsregistration/bdglparty/' + id,
    method: 'get'
  })
}

// 新增党支部会议登记本
export function addBdglparty(data) {
  return request({
    url: '/grassrootsregistration/bdglparty',
    method: 'post',
    data: data
  })
}

// 修改党支部会议登记本
export function updateBdglparty(data) {
  return request({
    url: '/grassrootsregistration/bdglparty',
    method: 'put',
    data: data
  })
}

// 删除党支部会议登记本
export function delBdglparty(id) {
  return request({
    url: '/grassrootsregistration/bdglparty/' + id,
    method: 'delete'
  })
}

// 导出党支部会议登记本
export function exportBdglparty(query) {
  return request({
    url: '/grassrootsregistration/bdglparty/export',
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
// 查找主持人
export function getZhuChiRen(data) {
  return request({
    url: '/grassrootsregistration/common/getZhuChiRen',
    method: 'get',
    params: { deptId:data}
  })
}

//领导查看
export function getPartyList(year) {
  return request({
    url: `/grassrootsregistration/bdglparty/getPartyList`,
    method: 'get',
    params:{year:year}
  })
}


// 领导列表
export function getPartyOnUnitList(query) {
  return request({
    url: '/grassrootsregistration/bdglparty/getPartyOnUnitList',
    method: 'get',
    params: query
  })
}