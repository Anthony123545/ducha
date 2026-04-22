import request from '@/utils/request'

// 查询工作任务列表
export function listWorkTask(query) {
  return request({
    url: '/workingArrangements/workTask/list',
    method: 'get',
    params: query
  })
}

// 查询工作任务详细
export function getWorkTask(id) {
  return request({
    url: '/workingArrangements/workTask/' + id,
    method: 'get'
  })
}

// 新增工作任务
export function addWorkTask(data) {
  return request({
    url: '/workingArrangements/workTask',
    method: 'post',
    data: data
  })
}

// 修改工作任务
export function updateWorkTask(data) {
  return request({
    url: '/workingArrangements/workTask',
    method: 'put',
    data: data
  })
}

// 获取多选单位下的人员
export function getUnitUsers(data) {
  return request({
    url: '/grassrootsregistration/common/getUnitUsers?deptIds='+data,
    method: 'get',
  })
}

// 删除工作任务
export function delWorkTask(id) {
  return request({
    url: '/workingArrangements/workTask/' + id,
    method: 'delete'
  })
}

// 导出工作任务
export function exportWorkTask(query) {
  return request({
    url: '/workingArrangements/workTask/export',
    method: 'get',
    params: query
  })
}

// 获取部门列表
export function getDeptList() {
  return request({
    url:"/system/dept/list",
    method:"get"
  })
}

// 获取当前部门用户
export function deptUser (deptId) {
  return request({
    url: `/grassrootsregistration/common/getUsers?deptId=${deptId}`,
    method: 'get'
  })
}

// 获取全部人员
export function allUser() {
  return request({
    url: "/system/user/list",
    method: 'get'
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
// 单人进度录入
export function singProgress(id) {
  return request({
    url:`/workingArrangements/workTask/getJinDu?id=${id}`,
    method:"get"
  })
}

// 多人进度录入
export function moreProgress(id) {
  return request({
    url:`/workingArrangements/workTask/getZJinDu?id=${id}`,
    method:"get"
  })
}
// 进度录入上传数据接口
export function uploadProgress(obj) {
  return request({
    url:"/workingArrangements/workProgress",
    method:"put",
    data:obj
  })
}
