import request from '@/utils/request'

// 查询工作任务计划列表
export function listJobTask(query) {
  return request({
    url: '/jobTaskRecord/jobTask/list',
    method: 'get',
    params: query
  })
}

// 查询工作任务计划详细
export function getJobTask(id) {
  return request({
    url: '/jobTaskRecord/jobTask/' + id,
    method: 'get'
  })
}

// 新增工作任务计划
export function addJobTask(data) {
  return request({
    url: '/jobTaskRecord/jobTask',
    method: 'post',
    data: data
  })
}

// 修改工作任务计划
export function updateJobTask(data) {
  return request({
    url: '/jobTaskRecord/jobTask',
    method: 'put',
    data: data
  })
}

// 删除工作任务计划
export function delJobTask(id) {
  return request({
    url: '/jobTaskRecord/jobTask/' + id,
    method: 'delete'
  })
}
// 查看总体工作任务计划
export function queryTaskProgress(id) {
  return request({
    url: `/jobTaskRecord/jobTask/queryTaskProgress/${id}`,
    method: 'get',
  })
}

