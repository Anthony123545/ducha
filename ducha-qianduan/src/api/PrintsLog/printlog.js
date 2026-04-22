import request from '@/utils/request'

// 查询集中文印记录列表
export function listPrintlog(query) {
  return request({
    url: '/PrintsLog/printlog/list',
    method: 'get',
    params: query
  })
}

export function personalList(query) {
  return request({
    url: '/PrintsLog/printlog/personalList',
    method: 'get',
    params: query
  })
}

// 查询集中文印记录详细
export function getPrintlog(id) {
  return request({
    url: '/PrintsLog/printlog/' + id,
    method: 'get'
  })
}

// 新增集中文印记录
export function addPrintlog(data) {
  return request({
    url: '/PrintsLog/printlog',
    method: 'post',
    data: data
  })
}

// 修改集中文印记录
export function updatePrintlog(data) {
  return request({
    url: '/PrintsLog/printlog',
    method: 'put',
    data: data
  })
}

// 删除集中文印记录
export function delPrintlog(id) {
  return request({
    url: '/PrintsLog/printlog/' + id,
    method: 'delete'
  })
}

// 导出集中文印记录
export function exportPrintlog(query) {
  return request({
    url: '/PrintsLog/printlog/export',
    method: 'get',
    params: query
  })
}

// 查询人员管理列表
export function listPeople(query) {
  return request({
    url: '/peopleManage/people/list',
    method: 'get',
    params: query
  })
}
