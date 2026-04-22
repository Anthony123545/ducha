import request from '@/utils/request'

// 查询人员来访登记列表
export function listBdglVisit(query) {
  return request({
    url: '/doormanManage/bdglVisit/list',
    method: 'get',
    params: query
  })
}

// 查询人员来访登记详细
export function getBdglVisit(id) {
  return request({
    url: '/doormanManage/bdglVisit/' + id,
    method: 'get'
  })
}

// 新增人员来访登记
export function addBdglVisit(data) {
  return request({
    url: '/doormanManage/bdglVisit',
    method: 'post',
    data: data
  })
}

// 修改人员来访登记
export function updateBdglVisit(data) {
  return request({
    url: '/doormanManage/bdglVisit',
    method: 'put',
    data: data
  })
}

// 删除人员来访登记
export function delBdglVisit(id) {
  return request({
    url: '/doormanManage/bdglVisit/' + id,
    method: 'delete'
  })
}

// 导出人员来访登记
export function exportBdglVisit(query) {
  return request({
    url: '/doormanManage/bdglVisit/export',
    method: 'get',
    params: query
  })
}


// 获取当前部门接待人
export function deptUser (deptId) {
  return request({
    url: `/grassrootsregistration/common/getZhuChiRen?deptId=${deptId}`,
    method: 'get'
  })
}


// 新增人员
export function addPeople(data) {
  return request({
    url: '/person/add',
    method: 'post',
    data: data
  })
}

// 查询人员来访登记列表
export function listPeople(query) {
  return request({
    url: '/person/list',
    method: 'get',
    params: query
  })
}

