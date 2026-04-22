import request from '@/utils/request'

// 查询月教育计划列表
export function listResponses(query) {
  return request({
    url: '/thought/responses/list',
    method: 'get',
    params: query
  })
}

// 查询月教育计划
export function getResponses(id) {
  return request({
    url: '/thought/responses/' + id,
    method: 'get'
  })
}

// 新增月教育计划
export function addResponses(data) {
  return request({
    url: '/thought/responses',
    method: 'post',
    data: data
  })
}

// 修改月教育计划
export function updateResponses(data) {
  return request({
    url: '/thought/responses',
    method: 'put',
    data: data
  })
}

// 删除月教育计划
export function delResponses(id) {
  return request({
    url: '/thought/responses/' + id,
    method: 'delete'
  })
}

// 导出月教育计划
export function exportResponses(query) {
  return request({
    url: '/thought/responses/export',
    method: 'get',
    params: query
  })
}

// 数据的展示
export function Exhibition() {
  return request({
    url: '/thought/thoughtPlan/getnumber',
    method: 'get',
  })
}