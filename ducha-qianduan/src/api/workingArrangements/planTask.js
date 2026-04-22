import request from '@/utils/request'

// 查询工作计划列表
export function listPlanTask(query) {
  return request({
    url: '/workingArrangements/planTask/list',
    method: 'get',
    params: query
  })
}

// 查询工作计划详细
export function getPlanTask(id) {
  return request({
    url: '/workingArrangements/planTask/' + id,
    method: 'get'
  })
}

// 新增工作计划
export function addPlanTask(data) {
  return request({
    url: '/workingArrangements/planTask',
    method: 'post',
    data: data
  })
}

// 修改工作计划
export function updatePlanTask(data) {
  return request({
    url: '/workingArrangements/planTask',
    method: 'put',
    data: data
  })
}

// 删除工作计划
export function delPlanTask(id) {
  return request({
    url: '/workingArrangements/planTask/' + id,
    method: 'delete'
  })
}

// 导出工作计划
export function exportPlanTask(query) {
  return request({
    url: '/workingArrangements/planTask/export',
    method: 'get',
    params: query
  })
}

// 单人进度录入
export function singProgress(id) {
  return request({
    url:`/workingArrangements/planTask/getJinDu?id=${id}`,
    method:"get"
  })
}

// 多人进度录入
export function moreProgress(id) {
  return request({
    url:`/workingArrangements/planTask/getZJinDu?id=${id}`,
    method:"get"
  })
}

// 进度录入上传数据接口
export function uploadProgress(obj) { 
  return request({
    url:"/workingArrangements/progress",
    method:"put",
    data:obj
  })
}
