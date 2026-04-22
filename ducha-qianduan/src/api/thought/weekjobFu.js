import request from '@/utils/request'

// 查询周工作附列表
export function listWeekjobFu(query) {
  return request({
    url: '/thought/weekjobFu/list',
    method: 'get',
    params: query
  })
}

// 查询周工作附详细
export function getWeekjobFu(id) {
  return request({
    url: '/thought/weekjobFu/' + id,
    method: 'get'
  })
}

// 新增周工作附
export function addWeekjobFu(data) {
  return request({
    url: '/thought/weekjobFu',
    method: 'post',
    data: data
  })
}

// 修改周工作附
export function updateWeekjobFu(data) {
  return request({
    url: '/thought/weekjobFu',
    method: 'put',
    data: data
  })
}

// 删除周工作附
export function delWeekjobFu(id) {
  return request({
    url: '/thought/weekjobFu/' + id,
    method: 'delete'
  })
}

// 导出周工作附
export function exportWeekjobFu(query) {
  return request({
    url: '/thought/weekjobFu/export',
    method: 'get',
    params: query
  })
}