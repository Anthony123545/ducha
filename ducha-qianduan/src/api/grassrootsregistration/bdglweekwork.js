import request from '@/utils/request'

// 查询一周工作安排列表
export function listBdglweekwork(query) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/list',
    method: 'get',
    params: query
  })
}

// 查询一周工作安排详细
export function getBdglweekwork(id) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/' + id,
    method: 'get'
  })
}

// 预览训练器材/教材登记薄
export function lookequipuntits(id) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/exportWeekwork?id=' + id,
    method: 'get'
  })
}
// 新增一周工作安排
export function addBdglweekwork(data) {
  return request({
    url: '/grassrootsregistration/bdglweekwork',
    method: 'post',
    data: data
  })
}

// 修改一周工作安排
export function updateBdglweekwork(data) {
  return request({
    url: '/grassrootsregistration/bdglweekwork',
    method: 'put',
    data: data
  })
}

// 删除一周工作安排
export function delBdglweekwork(id) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/' + id,
    method: 'delete'
  })
}

// 导出一周工作安排
export function exportBdglweekwork(query) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/export',
    method: 'get',
    params: query
  })
}
// 查询当前时段有么有数据
export function exportBdglgetWeekWork(query) {
  return request({
    url: '/grassrootsregistration/bdglweekwork/getWeekWork',
    method: 'get',
    params: query
  })
}
