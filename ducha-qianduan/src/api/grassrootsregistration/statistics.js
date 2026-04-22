import request from '@/utils/request'

// 查询军事训练月统计班列表
export function listStatistics(query) {
  return request({
    url: '/grassrootsregistration/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询军事训练月统计班详细
export function getStatistics(id) {
  return request({
    url: '/grassrootsregistration/statistics/' + id,
    method: 'get'
  })
}

// 新增军事训练月统计班
export function addStatistics(data) {
  return request({
    url: '/grassrootsregistration/statistics',
    method: 'post',
    data: data
  })
}

// 修改军事训练月统计班
export function updateStatistics(data) {
  return request({
    url: '/grassrootsregistration/statistics',
    method: 'put',
    data: data
  })
}

// 删除军事训练月统计班
export function delStatistics(id) {
  return request({
    url: '/grassrootsregistration/statistics/' + id,
    method: 'delete'
  })
}

// 导出军事训练月统计班
export function exportStatistics(query) {
  return request({
    url: '/grassrootsregistration/statistics/export',
    method: 'get',
    params: query
  })
}