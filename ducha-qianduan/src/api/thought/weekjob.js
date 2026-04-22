import request from '@/utils/request'

// 查询周工作列表
export function listWeekjob(query) {
  return request({
    url: '/thought/weekjob/list',
    method: 'get',
    params: query
  })
}

// 查询周工作详细
export function getWeekjob(id) {
  return request({
    url: '/thought/weekjob/' + id,
    method: 'get'
  })
}

// 新增周工作
export function addWeekjob(data) {
  return request({
    url: '/thought/weekjob',
    method: 'post',
    data: data
  })
}

// 修改周工作
export function updateWeekjob(data) {
  return request({
    url: '/thought/weekjob',
    method: 'put',
    data: data
  })
}

// 删除周工作
export function delWeekjob(id) {
  return request({
    url: '/thought/weekjob/' + id,
    method: 'delete'
  })
}

// 导出周工作
export function exportWeekjob(query) {
  return request({
    url: '/thought/weekjob/export',
    method: 'get',
    params: query
  })
}